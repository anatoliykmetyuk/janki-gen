package jorders.generators

import jentities._
import jentities.util.Implicits.localDatabase

trait GeneratorsData {

  val sampleVocab: Seq[Vocabulary] = "硬派厨 硬筆 硬変".split(" ").map {v => Vocabulary(v)}

  val sentencesSeed = Seq("する", "好き").map {w => Vocabulary(w)}

}