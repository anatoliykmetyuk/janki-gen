package jorders

/**
 * Contains keys for the map of orders.
 * Contains format constants for the notes fields.
 */
object Constants {

  val VOCABULARY = "vocabulary.txt"
  val KANJI      = "kanji.txt"
  val RADICALS   = "radicals.txt"
  val MEDIA      = "media"
  val EXCLUDE    = "exclude"

  /** Fields to specify the details of the orders */
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
  }

}