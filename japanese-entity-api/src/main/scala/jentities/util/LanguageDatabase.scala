package jentities.util

import java.io._

import scala.collection.JavaConversions._
import scala.xml._

import org.apache.commons.compress.archivers.tar._
import org.apache.commons.compress.compressors.gzip._
import org.apache.commons.io._

import jentities._

trait LanguageDatabase {

  def edict   : String => Option[String]

  def kanjidic: String => Option[Node]

  def kradfile: String => Option[String]

  def diagrams: String => Option[String]

  /**
   * Sentences whos all words belong to the input sequence.
   */ 
  def sentencesExclusive: Set[String] => Seq[Sentence]

  /**
   * Sentences with at least one of the words belong to the
   * input sequence.
   */
  def sentencesInclusive: Set[String] => Seq[Sentence]

}

object Implicits {
  implicit val localDatabase = LocalLanguageDatabase
}

/**
 * Provides convenient methods to construct various streams.
 */
trait LocalDatabaseAccess {

  /**
   * Open a file under /language-data as an input stream.
   */
  def langFile(name: String): InputStream =
    getClass.getClassLoader.getResourceAsStream(s"language-data/$name")

  /**
   * Decompress the given stream.
   */
  def gzip(is: InputStream) = new GzipCompressorInputStream(is)

  def withGzip[R](is: InputStream)(f: GzipCompressorInputStream => R): R = {
    val gis = gzip(is)
    try f(gis)
    finally gis.close()
  }

  /**
   * Decompresses a Gzip file and iterates over its lines.
   */
  def withGzipIterator[R](name: String, enc: String = "EUC_JP")(f: Iterator[String] => R): R = {
    val it = IOUtils.lineIterator(gzip(langFile(name)), enc)
    try f(it)
    finally it.close()
  }

  /**
   * Operates with the tgz archives.
   */
  def withTgz[R](name: String)(f: TarArchiveInputStream => R): R = {
    val is = new TarArchiveInputStream(gzip(langFile(name)))
    try f(is)
    finally is.close()
  }

}

/**
 * Language DB files access in a convenient form.
 */
object LocalLanguageDatabase extends LanguageDatabase with LocalDatabaseAccess {

  def asFunc[K](map: Map[String, K]): String => Option[K] = k => map.get(k)

  // Edict
  lazy val edictMap: Map[String, String] = withGzipIterator("edict2.gz") {_
    .drop(1)
    .map     {x => x.takeWhile(_ != ' ') -> x}
    .flatMap {case (k, v) => k.split(";").map(_.takeWhile(_ != '(') -> v)}  // Handling cases like 硝子体;ガラス体 - SEMICOLON!!!
    .toMap
  }
  lazy val edict: String => Option[String] = asFunc(edictMap)

  // Kanjidic
  lazy val kanjidicMap: Map[String, Node] = withGzipIterator("kanjidic2.xml.gz", "utf-8") {it =>
    val rawXml = XML loadString it.drop(323).filter(!_.startsWith("<!-- Entry")).mkString("\n")
    (rawXml \ "character").map {c => (c \ "literal").head.text -> c}.toMap
  }
  lazy val kanjidic: String => Option[Node] = asFunc(kanjidicMap)

  // Kradfile
  lazy val kradfileMap: Map[String, String] = withGzipIterator("kradfile-u.gz", "UTF-8") {_
    .dropWhile(_.head == '#')
    .map {x => x.takeWhile(_ != ' ') -> x}
    .toMap
  }
  lazy val kradfile: String => Option[String] = asFunc(kradfileMap)

  // Diagrams
  lazy val diagramsMap: Map[String, String] = withTgz("colorized-kanji-contrast.tgz") {tis =>
    tis.getNextEntry

    var result = Map[String, String]()
    while (tis.getNextEntry != null) result +=
      tis.getCurrentEntry.getName.dropWhile(_ != '/').drop(1).dropRight(".svg".size) -> IOUtils.toString(tis)

    result
  }
  lazy val diagrams: String => Option[String] = asFunc(diagramsMap)

  // Sentences
  lazy val sentencesMapJpn: Map[Long, (String, Seq[String])] = withGzipIterator("tatoeba/sentences.gz", "UTF-8") {_
    .toSeq
    .map    {_ split "\t"}
    .filter {_(1) == "jpn"}
    .map    {arr => arr(0).toLong -> (arr(2), if (arr.length >= 4) arr(3).split(";").toSeq else Seq[String]())}  // Id -> (sentence, words)
    .toMap
  }

  lazy val sentencesMapEng: Map[Long, String] = withGzipIterator("tatoeba/sentences.gz", "UTF-8") {_
    .toSeq
    .map    {_ split "\t"}
    .filter {_(1) == "eng"}
    .map    {arr => arr(0).toLong -> arr(2)}
    .toMap
  }

  lazy val linksMap: Map[Long, Seq[Long]] = withGzipIterator("tatoeba/links.gz", "UTF-8") {_
    .toSeq
    .map     {_.split("\t").map(_.toLong)}
    .groupBy {_.head}
    .map     {case (k, seq) => k -> seq.map(_(1))}
    .toMap
  }


  lazy val sentencesExclusive: Set[String] => Seq[jentities.Sentence] = in => sentencesMapJpn
    .filter  {case (_, (_, words)) => !words.isEmpty && words.forall(in)}
    .toSeq
    .flatMap {case (id, (sentence, _)) =>
      linksMap(id).map {tid => Sentence(sentence, sentencesMapEng(tid))}
    }

  lazy val sentencesInclusive: Set[String] => Seq[jentities.Sentence] = ???
}