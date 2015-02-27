package jentities

import jentities.util.LanguageDatabase._

trait KanjiEntry extends Entity {
  def vocabulary         = kanjidic
  lazy val decomposition = kradfile.find(_.startsWith(name))

  def meanings: Seq[String] = vocabEntry.map {entry =>
    """\{([^\{\}]+)\}""".r.findAllMatchIn(entry).map(_.group(1)).toList
  }.getOrElse(Nil)

  def diagram: Option[String] = kanjiDiagram(name)

  def elements: Seq[Radical] = decomposition.map {_
    .split(" ")
    .toSeq.distinct
    .drop(2)
    .diff(name)
    .map     {r => Radical(r)}
    .flatMap {r => r.elements :+ r}
  }.getOrElse(Nil)

}

case class Radical(name: String) extends KanjiEntry