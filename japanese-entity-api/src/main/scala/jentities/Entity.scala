package jentities

import jentities.util.LanguageDatabase

trait Entity {
  type DictEntryForm

  implicit val languageDatabase: LanguageDatabase
  import languageDatabase._

  val name      : String
  def vocabulary: String => Option[DictEntryForm]

  lazy val vocabEntry: Option[DictEntryForm] = vocabulary(name)
  lazy val found = vocabEntry.isDefined
}