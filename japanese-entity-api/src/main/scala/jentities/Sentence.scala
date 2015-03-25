package jentities

import jentities.util.LanguageDatabase

// This is a complete model, no lazy lookup in the dictionaries
case class Sentence(japanese: String, english : String) {
  def reading: String = ???
}