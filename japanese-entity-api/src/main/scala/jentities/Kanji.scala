package jentities

import jentities.util.JCharactersUtils._

case class Kanji(name: String) extends KanjiEntry {

  def readings: Seq[String] = vocabEntry.map {entry =>
    entry.split(" ").toSeq.filter(_.exists(isKana))
  }.getOrElse(Nil)

}