package jorders.output

import java.io.OutputStream

import org.apache.commons.io._
import org.apache.commons.compress.archivers.zip._

object ZipOutput {

  def writeResultToStream(result: Map[String, Any], stream: OutputStream) {
    val zos = new ZipArchiveOutputStream(stream)
    try writeMap(result, zos)
    finally zos.close
  }

  def writeMap(map: Map[String, Any], stream: ZipArchiveOutputStream, prefix: String = "") {
    // Turn lists into strings
    val refinedMap = map.map {case (k, l: Seq[String]) => k -> l.mkString("\n"); case x => x}
    for ((name, content) <- refinedMap) content match {
      case str   : String           => writeText(str   , stream, s"$prefix$name")
      case folder: Map[String, Any] => writeMap (folder, stream, s"$prefix$name/")
    }
  }

  def writeText(contents: String, stream: ZipArchiveOutputStream, path: String) {
    stream.putArchiveEntry(new ZipArchiveEntry(path))
    IOUtils.write(contents, stream)
    stream.closeArchiveEntry()
  }

}