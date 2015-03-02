package jorders.generators

import jentities._

import jorders.Constants.Fields._

object KanjiGenerator extends RadicalsGenerator {
  override type T = Kanji

  override val tokenProcessors = super.tokenProcessors ++ Map[String, Kanji => String](
    R -> {v => v.readings.mkString("; ")}
  )

  def extract(e: Entity) = e match {
    case e: Vocabulary => e.elements
    case e: Kanji      => Seq(e)
    case _             => Nil
  }
}