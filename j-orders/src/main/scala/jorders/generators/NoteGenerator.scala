package jorders.generators

import jentities._

/**
 * Is used to process an order for a given order type and produce notes from it.
 */
trait NoteGenerator extends Generator[Seq[String]] {
  type T <: Entity

  def tokenProcessors: Map[String, T => String]

  val separator: String = "\t"

  def extract(x: Entity): Seq[T]

  /**
   * Payload is a vocabulary to work with, order details is a
   * specifically formated String. It is a space-separated sequence of
   * tokes, each token denotes a field of the resulting note.
   */
  def generate(payload: Seq[Entity], orderDetails: String): Seq[String] =
    payload.flatMap(extract).filter(_.found).map {processOne(_, orderDetails.split(" "))}

  /**
   * Processes one vocabulary term from the payload.
   */
  def processOne(term: T, orderTokens: Seq[String]): String =
    orderTokens.map {tokenProcessors(_).apply(term)}.mkString(separator)

}