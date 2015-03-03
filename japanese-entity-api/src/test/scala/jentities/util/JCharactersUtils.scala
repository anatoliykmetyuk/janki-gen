package jentities.util

import org.scalatest._

class JCharactersUtilsSpec extends FlatSpec with Matchers with Inspectors {
  import JCharactersUtils._
  import LanguageDatabase._

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
    isKanji('木') shouldBe true
  }

}