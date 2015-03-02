package jentities.util

import java.io._

import scala.collection.JavaConversions._

import org.apache.commons.compress.archivers.tar._
import org.apache.commons.compress.compressors.gzip._
import org.apache.commons.io._

/**
 * Provides convenient methods to construct various streams.
 */
trait LanguageDatabaseAccess {

  /**
   * Open a file under /language-data as an input stream.
   */
  private def langFile(name: String): InputStream =
    getClass.getClassLoader.getResourceAsStream(s"language-data/$name")

  /**
   * Decompress the given stream.
   */
  private def gzip(is: InputStream): InputStream = new GzipCompressorInputStream(is)

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
object LanguageDatabase extends LanguageDatabaseAccess {

  lazy val edict: Map[String, String] = withGzipIterator("edict2.gz") {_
    .drop(1)
    .map     {x => x.takeWhile(_ != ' ') -> x}
    .flatMap {case (k, v) => k.split(";").map(_.takeWhile(_ != '(') -> v)}  // Handling cases like 硝子体;ガラス体 - SEMICOLON!!!
    .toMap
  }

  lazy val kanjidic: Map[String, String] = withGzipIterator("kanjidic.gz") {_
    .drop(1)
    .map {x => x.takeWhile(_ != ' ') -> x}
    .toMap
  }

  lazy val kradfile: Map[String, String] = withGzipIterator("kradfile-u.gz", "UTF-8") {_
    .dropWhile(_.head == '#')
    .map {x => x.head.toString -> x}
    .toMap
  }

  lazy val diagrams: Map[String, String] = withTgz("colorized-kanji-contrast.tgz") {tis =>
    tis.getNextEntry

    var result = Map[String, String]()
    while (tis.getNextEntry != null) result +=
      tis.getCurrentEntry.getName.dropWhile(_ != '/').drop(1).dropRight(".svg".size) -> IOUtils.toString(tis)

    result
  }

}