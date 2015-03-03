package jorders.generators

import jentities._

/**
 * Is used to process an order for a given order type and produce notes from it.
 */
trait NoteGenerator extends Generator[Seq[String]] {
  type T <: Entity

  def tokenProcessors: Map[String, T => String]

  val separator: String = "\t"

  // We pass the exclude here - there should be a better way
  // to filter the excluded stuff from the transitive elements...
  def extract(x: Entity, exclude: Seq[Entity]): Seq[T]

  /**
   * Payload is a vocabulary to work with, order details is a
   * specifically formated String. It is a space-separated sequence of
   * tokes, each token denotes a field of the resulting note.
   */
  def generate(payload: Seq[Entity], orderDetails: String, exclude: Seq[Entity] = Nil): Seq[String] =
    payload
      .distinct.diff (exclude)
      .filter(_.found)
      .flatMap       (extract(_, exclude))
      .distinct.diff (exclude)  // Same operation twice, to prevent transitive elements of the excluded elements from generation
      .filter(_.found)
      .map           {processOne(_, orderDetails.split(" "))}

  /**
   * Processes one vocabulary term from the payload.
   */
  def processOne(term: T, orderTokens: Seq[String]): String =
    orderTokens.map {tokenProcessors(_).apply(term)}.mkString(separator)

}