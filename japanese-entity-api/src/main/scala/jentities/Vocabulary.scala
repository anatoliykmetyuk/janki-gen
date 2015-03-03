package jentities

import jentities.util.LanguageDatabase._
import jentities.util.JCharactersUtils._

case class Vocabulary(name: String) extends Entity {
  type DictEntryForm = String
  def vocabulary     = edict

  def reading: Option[String] = vocabEntry.flatMap {entry =>
    """\[([^\[\]]+)\]""".r.findFirstMatchIn(entry).map(_.group(1))
  }

  def meanings: Seq[String] = vocabEntry.map {entry =>
    entry
      .dropWhile(_ != '/')                    // Drop Kanji and Kana readings
      .split("/").dropRight(1).mkString("/")  // Remove the weird reference at the end of each entry
      .split("""/\(""").drop(1).map("(" + _)  // Split the different meanings of the word, each starts with /(something)
      .toSeq
  }.getOrElse(Nil)

  def elements: Seq[Kanji] = name.filter(isKanji).distinct.map {c => Kanji(c.toString)}
  
}
