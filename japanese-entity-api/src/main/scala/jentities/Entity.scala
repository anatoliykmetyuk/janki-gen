package jentities

import jentities.util.LanguageDatabase._

trait Entity {
  val name      : String
  def vocabulary: Map[String, String]

  lazy val vocabEntry: Option[String] = vocabulary.get(name)
  lazy val found = vocabEntry.isDefined
}