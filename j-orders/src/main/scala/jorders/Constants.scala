package jorders

/**
 * Contains keys for the map of orders. The map of orders contains order keys
 * (VOCABULARY, KANJI, RADICALS, MEDIA, EXCLUDE) and the order specifications
 * as the values. For example:<br/>
 * Map(<br/>
 *   VOCABULARY -> s"$I $M $R $E"<br/>
 * )
 */
object Constants {

  /**
   * Orders the vocabulary words cards generation. The value must
   * be a string with space-separated desired cards' fields specification.
   * For example: s"${Fields.I} ${Fields.M} $R $E".
   */
  val VOCABULARY = "vocabulary.txt"

  /**
   * Orders the Kanji cards generation. The value must
   * be a string with space-separated desired cards' fields specification.
   * For example: s"${Fields.I} ${Fields.M} $R $E".
   */
  val KANJI      = "kanji.txt"

  /**
   * Orders the Radicals cards generation. The value must
   * be a string with space-separated desired cards' fields specification.
   * For example: s"${Fields.I} ${Fields.M} $E".
   */
  val RADICALS   = "radicals.txt"

  /**
   * Orders the generation of the stroke diagrams for the kanjis and
   * the radicals. The value is ignored.
   */
  val MEDIA      = "media"
  
  /**
   * Defines the entities to exclude. The value is a Seq[Entity] of the
   * entities to exclude.
   */
  val EXCLUDE    = "exclude"

  val SENTENCES_EXCLUSIVE  = "sentences_exclusive.txt"
  val SENTENCES_INCLUSIVE  = "sentences_inclusive.txt"

  /**
   * Variables to specify what fields should the ordered cards contain.
   */
  object Fields {
    /** Identity, "name" of the element */
    val I = "i"

    /** Meaning */
    val M = "m"

    /** Reading */
    val R = "r"

    /** Elements */
    val E = "e"

    /** Diagram file HTML code */
    val Di = "di"

    /** Count of the results required */
    val C = "c"
  }

}