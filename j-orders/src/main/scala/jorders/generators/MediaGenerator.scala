package jorders.generators

import jentities._

object MediaGenerator extends Generator[Map[String, String]] {
  def generate(payload: Seq[Vocabulary], orderDetails: String) = payload
    .flatMap {_.elements}
    .flatMap {e => e.allElements :+ e}
    .filter  {_.diagram.isDefined}
    .map     {e => s"${e.utfCode}.svg" -> e.diagram.get}
    .toMap
}