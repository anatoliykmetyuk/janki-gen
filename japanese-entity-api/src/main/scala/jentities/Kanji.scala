package jentities

import jentities.util.JCharactersUtils._

/**
 * A Kanji.
 */
case class Kanji(name: String) extends KanjiEntry {

  /**
   * All the readings of this kanji.
   */
  def readings: Seq[String] = vocabEntry.map {n => (n \\ "reading")
    .filter {x =>
      val attr = x.attribute("r_type").get.head.text
      attr == "ja_on" || attr == "ja_kun"
    }
    .map    {_.text}
  }.getOrElse(Nil)

}