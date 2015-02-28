package jorders.generators

import jentities._

import jorders.Constants.Fields._

// We are going to reuse it for the KanjiGenerator, so it will be a trait.
trait RadicalsGenerator extends NoteGenerator {
  type T <: KanjiEntry

  def tokenProcessors = Map[String, T => String](
    I  -> {v => v.name},
    M  -> {v => v.meanings.mkString("; ")},
    E  -> {v => v.elements.map(_.name).mkString},
    Di -> {v => s"""<img src="${v.utfCode}.svg"/>"""}
  )

}

object RadicalsGenerator extends RadicalsGenerator {
  type T = Radical

  def extract(e: Vocabulary) = e.elements.flatMap(_.allElements)
}