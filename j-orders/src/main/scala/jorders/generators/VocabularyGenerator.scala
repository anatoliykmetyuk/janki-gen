package jorders.generators

import jentities._

import jorders.Constants.Fields._

object VocabularyGenerator extends NoteGenerator {
  type T = Vocabulary

  val tokenProcessors = Map[String, Vocabulary => String](
    I -> {v => v.name},
    M -> {v => v.meanings.mkString("; ")},
    R -> {v => v.reading.get},
    E -> {v => v.elements.map(_.name).mkString}
  )

  def extract(e: Entity, exclude: Seq[Entity]) = e match {
    case e: Vocabulary => Seq(e)
    case _             => Nil
  }
}