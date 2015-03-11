package jentities

import jentities.util.LanguageDatabase
import jentities.util.JCharactersUtils._

/**
 * A single Japanese word.
 */
case class Vocabulary(name: String)(implicit val languageDatabase: LanguageDatabase) extends Entity {
  import languageDatabase._

  type DictEntryForm = String
  def vocabulary     = edict

  /**
   * The reading of this word.
   */
  def reading: Option[String] =
    if (name.forall(isKana)) Some(name)
    else vocabEntry.flatMap {entry =>
      """\[([^\[\]]+)\]""".r.findFirstMatchIn(entry).map(_.group(1))
    }

  /**
   * English translations of this word.
   */
  def meanings: Seq[String] = vocabEntry.map {entry =>
    entry
      .dropWhile(_ != '/')                    // Drop Kanji and Kana readings
      .split("/").dropRight(1).mkString("/")  // Remove the weird reference at the end of each entry
      .split("""/\(""").drop(1).map("(" + _)  // Split the different meanings of the word, each starts with /(something)
      .toSeq
  }.getOrElse(Nil)

  /**
   * Kanjis this word is composed of.
   */
  def elements: Seq[Kanji] = name.filter(isKanji).distinct.map {c => Kanji(c.toString)}
  
}
