package jorders.generators

import jentities._

object MediaGenerator extends Generator[Map[String, String]] {
  def generate(payload: Seq[Entity], orderDetails: String) = payload
    .flatMap {case v: Vocabulary => v.elements; case x => Seq(x)}    // Reduce vocab to kanjis
    .flatMap {case e: Kanji => e.allElements :+ e; case x => Seq(x)} // Generate radicals from kanjis
    .asInstanceOf[Seq[KanjiEntry]]
    .filter  {_.diagram.isDefined}
    .map     {e => s"${e.utfCode}.svg" -> e.diagram.get}
    .toMap
}