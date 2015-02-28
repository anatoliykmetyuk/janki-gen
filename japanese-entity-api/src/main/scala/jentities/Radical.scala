package jentities

import jentities.util.LanguageDatabase._

trait KanjiEntry extends Entity {
  def vocabulary         = kanjidic
  lazy val decomposition = kradfile.find(_.startsWith(name))

  def meanings: Seq[String] = vocabEntry.map {entry =>
    """\{([^\{\}]+)\}""".r.findAllMatchIn(entry).map(_.group(1)).toList
  }.getOrElse(Nil)

  def diagram: Option[String] = kanjiDiagram(name)

  def utfCode: String = (name.head | 0x1000000).toHexString.drop(1)

  def elements: Seq[Radical] = decomposition.map {_
    .split(" ")
    .toSeq.distinct
    .drop(2)
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
    // elements.flatMap {r => r.allElements :+ r}.distinct
  }
}

case class Radical(name: String) extends KanjiEntry