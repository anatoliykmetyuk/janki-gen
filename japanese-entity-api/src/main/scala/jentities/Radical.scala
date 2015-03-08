package jentities

import scala.xml._

import jentities.util.LanguageDatabase._

trait KanjiEntry extends Entity {
  type DictEntryForm     = Node
  def vocabulary         = kanjidic
  lazy val decomposition = kradfile.get(name)

  def meanings: Seq[String] = vocabEntry.map {n => (n \\ "meaning")
    .filter {_.attributes.isEmpty}
    .map    {_.text}
  }.getOrElse(Nil)

  def diagram: Option[String] = diagrams.get(name)

  def utfCode: String = name.flatMap {c => (c | 0x10000).toHexString.drop(1)}
  // (name.head | 0x1000000).toHexString.drop(1)

  def elements: Seq[Radical] = decomposition.map {_
    .split(" ")
    .toSeq
    .drop(2)
    .distinct
    .map {r => Radical(r)}
  }.getOrElse(Nil)

  def allElements: Seq[Radical] = {
    // We are expanding the elements to some reasonable limit
    // in order to avoid an infinite loop in case of cyclic
    // relationships between some elements.
    def expand(ets: Seq[Radical], level: Int, limit: Int): Seq[Radical] =
      if (level >= limit) ets
      else ets.flatMap {e => expand(e.elements, level + 1, limit) :+ e}.distinct

    expand(elements, 0, 10)
  }
}

case class Radical(name: String) extends KanjiEntry {
  // Don't include this in the elements output
  override def elements = super.elements.diff(Seq(this))
}