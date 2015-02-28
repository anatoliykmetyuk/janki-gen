package jorders.generators

import jentities._

trait GeneratorsData {

  val sampleVocab: Seq[Vocabulary] = "硬派厨 硬筆 硬変".split(" ").map {v => Vocabulary(v)}

}