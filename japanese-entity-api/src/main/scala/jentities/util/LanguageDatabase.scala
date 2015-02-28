package jentities.util

import java.io._

import scala.collection.JavaConversions._

import org.apache.commons.compress.archivers.tar._
import org.apache.commons.compress.compressors.gzip._
import org.apache.commons.io._

/**
 * Provides convenient methods to access and
 * process the language data files available.
 */
trait LanguageDatabaseAccess {

  /**
   * Open a file under /language-data as an input stream.
   */
  def langFile(name: String): InputStream =
    getClass.getClassLoader.getResourceAsStream(s"language-data/$name")

  /**
   * Decompress the given stream.
   */
  def gzip(is: InputStream): InputStream = new GzipCompressorInputStream(is)

  /**
   * Iterator over the lines of the given file.
   */
  def iterator(is: InputStream, enc: String = "EUC_JP"): Iterator[String] =
    IOUtils.lineIterator(is, enc)

  /**
   * Decompresses a Gzip file and iterates over its lines.
   */
  def gzipIterator(name: String, enc: String = "EUC_JP"): Iterator[String] =
    iterator(gzip(langFile(name)), enc)

}

/**
 * Convenience methods and classes to work with tgz files.
 */
trait TarAccess {this: LanguageDatabaseAccess =>

  def tar(is: InputStream): TarArchiveInputStream = new TarArchiveInputStream(is)

  def tgz(name: String)   : TarArchiveInputStream = tar(gzip(langFile(name)))

}

/**
 * Language DB files access in a convenient form.
 */
object LanguageDatabase extends LanguageDatabaseAccess with TarAccess {

  def edict    = gzipIterator("edict2.gz"                   )
  def kanjidic = gzipIterator("kanjidic.gz"                 )
  def kradfile = gzipIterator("kradfile-u.gz", "UTF-8"      )
  def diagrams = tgz         ("colorized-kanji-contrast.tgz")

  lazy val edictMap: Map[String, String] = edict
    .drop(1)
    .map {x => x.takeWhile(c => c != ' ' && c != ';' && c != '(') -> x}
    .toMap

  lazy val kanjidicMap: Map[String, String] = kanjidic
    .drop(1)
    .map {x => x.takeWhile(_ != ' ') -> x}
    .toMap

  lazy val kradfileMap: Map[String, String] = kradfile
    .dropWhile(_.head == '#')
    .map {x => x.head.toString -> x}
    .toMap

  lazy val diagramsMap: Map[String, String] = {
    val tis = diagrams
    tis.getNextEntry

    var result = Map[String, String]()
    while (tis.getNextEntry != null) result +=
      tis.getCurrentEntry.getName.dropWhile(_ != '/').drop(1).dropRight(".svg".size) -> IOUtils.toString(tis)

    result
  }

  /**
   * Reads an SVG diagram of the given kanji.
   */
  def kanjiDiagram(kanji: String) = {
    def loop(tis: TarArchiveInputStream): Option[String] =
      // If reached the end of the archive, nothing was found
      if (tis.getNextEntry == null) None

      // If the name of the file ends with the required kanji, read it and return
      else if (tis.getCurrentEntry.getName.endsWith(s"$kanji.svg")) Some(IOUtils.toString(tis))

      // Otherwise, continue the search
      else loop(tis)

    loop(diagrams)
  }

}