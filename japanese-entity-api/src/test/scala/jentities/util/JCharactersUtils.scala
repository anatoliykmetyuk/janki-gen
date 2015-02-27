package jentities.util

import org.scalatest._

class JCharactersUtilsSpec extends FlatSpec with Matchers with Inspectors {
  import JCharactersUtils._

  "JCharactersUtils" should "determine Hiragana correctly" in {
    isHiragana('ひ') shouldBe true
  }

  it should "determine Katakana correctly" in {
    isKatakana('ク') shouldBe true
  }

  it should "determine Kana correctly" in {
    isKana('ヒ') shouldBe true
  }

  it should "determine Kanji correctly" in {
    forAll {
      LanguageDatabase.kanjidic.map(_.head).toSeq.diff(Seq('#')).mkString
    } {c => isKanji(c) shouldBe true}
  }

}