package jentities

import scala.xml._

import jentities.util.LanguageDatabase

/**
 * A base trait for Kanjis and Radicals.
 */
trait KanjiEntry extends Entity {
  import languageDatabase._

  type DictEntryForm     = Node
  def vocabulary         = kanjidic
  lazy val decomposition = kradfile(name)

  /**
   * The meanings of this entry.
   */
  def meanings: Seq[String] = vocabEntry.map {n => (n \\ "meaning")
    .filter {_.attributes.isEmpty}
    .map    {_.text}
  }.getOrElse(Nil)

  /**
   * The stroke order diagram of this entry in the SVG format.
   */ 
  def diagram: Option[String] = diagrams(name)

  def utfCode: String = name.flatMap {c => (c | 0x10000).toHexString.drop(1)}

  /**
   * The elements this entry is composed of. Note that even some radicals
   * are not atomic and can further be decomposed to other elements.
   */
  def elements: Seq[Radical] = decomposition.map {_
    .split(" ")
    .toSeq
    .drop(2)
    .distinct
    .map {r => Radical(r)}
  }.getOrElse(Nil)

  /**
   * The elements this entry is composed of, including the transitive ones.
   * For example, if A is composed of B and C, and B is composed of E and D, this
   * method will return Seq(E, D, B, C), in that order.
   */
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

/**
 * A Radical.
 */
case class Radical(name: String)(implicit val languageDatabase: LanguageDatabase) extends KanjiEntry {
  // Don't include this in the elements output
  override def elements = super.elements.diff(Seq(this))
}