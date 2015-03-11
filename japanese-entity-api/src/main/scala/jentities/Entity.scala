package jentities

import jentities.util.LanguageDatabase

trait Entity {
  type DictEntryForm

  implicit val languageDatabase: LanguageDatabase
  import languageDatabase._

  val name      : String
  def vocabulary: PartialFunction[String, DictEntryForm]

  lazy val vocabEntry: Option[DictEntryForm] = vocabulary.get(name)
  lazy val found = vocabEntry.isDefined
}