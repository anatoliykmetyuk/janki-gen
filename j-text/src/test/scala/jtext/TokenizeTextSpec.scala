package jtext

import org.scalatest._
import jentities._

class TokenizeTextSpec extends FlatSpec with Matchers with TokenizeTextSpecHelpers {

  "Text tokenizer" should "tokenize text correctly" in {
    TextTokenizer(sampleText) should contain theSameElementsAs Seq(
      "太陽",
      "昨日",
      "眩しい",
      "照りつける",
      "始める",
      "真っ白",
      "今",
      "着替える",
      "Tシャツ",
      "すぐ"
    ).map {x => Vocabulary(x)}
  }

}

trait TokenizeTextSpecHelpers {
  
  val sampleText ="""
太陽が
昨日より
眩しく照りつけ始めたら
真っ白な
Tシャツに
今すぐ着替えて
"""

}