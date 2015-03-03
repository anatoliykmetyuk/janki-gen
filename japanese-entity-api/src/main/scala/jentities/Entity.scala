package jentities

import jentities.util.LanguageDatabase._

trait Entity {
  type DictEntryForm

  val name      : String
  def vocabulary: Map[String, DictEntryForm]

  lazy val vocabEntry: Option[DictEntryForm] = vocabulary.get(name)
  lazy val found = vocabEntry.isDefined
}