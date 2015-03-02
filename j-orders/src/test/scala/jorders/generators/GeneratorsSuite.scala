package jorders.generators

import org.scalatest._

import jentities._

import jorders.Constants.Fields._

class GeneratorsSuite extends FlatSpec with Matchers with GeneratorsData {

  "Vocabulary generator" should "work" in {
    VocabularyGenerator.generate(sampleVocab, s"$I $M $R $E") shouldBe Seq(
      "硬派厨\t(n) (sl) (derog) Internet tough guy\tこうはちゅう\t硬派厨",
      "硬筆\t(n) pen or pencil\tこうひつ\t硬筆",
      "硬変\t(n,vs) cirrhosis\tこうへん\t硬変"
    )
  }

  "Kanji generator" should "work" in {
    KanjiGenerator.generate(sampleVocab, s"$I $M $Di") should contain allOf (
      "硬\tstiff; hard\t<img src=\"00786c.svg\"/>",
      "派\tfaction; group; party; clique; sect; school\t<img src=\"006d3e.svg\"/>"
    )
  }

  it should "also accept kanjis in its payload apart from the words" in {
    KanjiGenerator.generate(Seq(Kanji("硬")), s"$I $M $Di") should contain ("硬\tstiff; hard\t<img src=\"00786c.svg\"/>")
  }

  "Radicals generator" should "work" in {
    RadicalsGenerator.generate(sampleVocab, s"$I $M") should contain allOf (
      "一\tone; one radical (no.1)",
      "口\tmouth",
      "石\tstone",
      "日\tday; sun; Japan; counter for days"
    )
  }

  it should "also accept radicals in its payload" in {
    RadicalsGenerator.generate(Seq(Radical("口")), s"$I $M") should contain ("口\tmouth")    
  }

  it should "also accept kanjis in its payload" in {
    RadicalsGenerator.generate(Seq(Kanji("一")), s"$I $M") should contain ("一\tone; one radical (no.1)")
  }

  "Media generator" should "work" in {
    MediaGenerator.generate(Seq(Vocabulary("一つ")), "").contains("004e00.svg") shouldBe true
  }

  "Exclusion" should "work" in {
    VocabularyGenerator.generate(sampleVocab, s"$I $M $R $E", Seq(Vocabulary("硬変"))) should not contain ("硬変\t(n,vs) cirrhosis\tこうへん\t硬変")
  }

}
