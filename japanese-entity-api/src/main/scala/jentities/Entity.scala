package jentities

import jentities.util.LanguageDatabase._

trait Entity {
  val name      : String
  def vocabulary: Iterator[String]

  lazy val vocabEntry: Option[String] = vocabulary.find(_.startsWith(name))
  lazy val found = vocabEntry.isDefined
}