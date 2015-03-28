package jorders

import jentities._
import jentities.util.LanguageDatabase

import Constants._
import generators._

/**
 * Processes the orders for notes generation.
 * Results in the generation of a Map, with the Constants object.
 * Think of it as of a directory with files. It is supposed
 * to be converted into it.
 * @see Constants for more information on composing orders.
 */
object ProcessOrder {

  /**
   * @param payload a sequence of words or kanjis to be processed.
   * @param order an order.
   */
  def apply(payload: Seq[String], order: Map[String, Any])(implicit ldb: LanguageDatabase): Map[String, Any] = {
    val dPayload = payload.map {e =>
           if (Vocabulary(e).found) Vocabulary(e)
      else if (Kanji     (e).found) Kanji     (e)
      else                          Vocabulary(e)
    }   // Domain payload

    processEntities(dPayload, order)
  }

  /**
   * @param payload a sequence of entities to be processed.
   * @param order an order.
   */
  def processEntities(payload: Seq[Entity], order: Map[String, Any])(implicit ldb: LanguageDatabase): Map[String, Any] = {
    var result = Map[String, Any]()

    val noteGenerators = Map(
      VOCABULARY           -> VocabularyGenerator,
      KANJI                -> KanjiGenerator,
      RADICALS             -> RadicalsGenerator,
      MEDIA                -> MediaGenerator,
      SENTENCES_EXCLUSIVE  -> new SentenceGeneratorExclusive(ldb),
      SENTENCES_INCLUSIVE  -> new SentenceGeneratorInclusive(ldb)
    )
    def generateOrder(key: String) = if (noteGenerators contains key)
      result += key -> noteGenerators(key).generate (
        payload,
        order(key).toString,
        order.get(EXCLUDE).map(_.asInstanceOf[Seq[Entity]]).getOrElse(Nil)
      )

    for (k <- order.keys) generateOrder(k)
    
    result
  }

}

/** For testing purposes */
object Sample {
  import Constants.Fields._

  val order = Map(
    VOCABULARY -> s"$I $M $R $E",
    KANJI      -> s"$I $M $R $E $Di",
    RADICALS   -> s"$I $M $Di",
    MEDIA      -> "",
    EXCLUDE    -> List(
      // Kanji("女")
    ),
    SENTENCES_EXCLUSIVE -> s"$C=100"
  )

  val words = Seq(
    "女の子",
    "少女",
    "子供"
  )

  val words2 = Seq(
    "太陽",
    "昨日",
    "眩しい",
    "照りつける",
    "始める",
    "真っ白",
    "今すぐ",
    "着替える",
    "君",
    "誘う",
    "海沿い",
    "国道",
    "まだまだ",
    "空く",
    "乗り",
    "潮風",
    "追いかける",
    "誰",
    "早く",
    "夏",
    "探す"
  )

  def doo(m: Map[String, Any], key: String) = m(key).asInstanceOf[Seq[String]].mkString("\n")

}