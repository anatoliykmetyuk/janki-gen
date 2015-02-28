package jorders.generators

import jentities._

/**
 * Is used to generate whatever content from a vocabulary payload.
 */
trait Generator[+T] {

  def generate(payload: Seq[Vocabulary], orderDetails: String): T

}