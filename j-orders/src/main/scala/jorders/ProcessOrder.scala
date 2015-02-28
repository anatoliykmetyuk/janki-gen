package jorders

import jentities._

import Constants._
import generators._

/**
 * Processes the orders for notes generation.
 * Results in the generation of a Map, with the Constants object.
 * Think of it as of a directory with files. It is supposed
 * to be converted into it anyway.
 */
object ProcessOrder {

  def apply(payload: Seq[String], order: Map[String, String]) {
    val dPayload = payload.map {e => Vocabulary(e)}   // Domain payload

    var result = Map[String, Any]()

    val noteGenerators = Map(
      VOCABULARY -> VocabularyGenerator,
      KANJI      -> KanjiGenerator,
      RADICALS   -> RadicalsGenerator,
      MEDIA      -> MediaGenerator
    )
    def generateOrder(key: String) =
      result += key -> noteGenerators(key).generate(dPayload, order(key))

    for (k <- order.keys) generateOrder(k)
  }

}