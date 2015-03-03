package jentities.util

import org.scalatest._

class LanguageDatabaseSpec extends FlatSpec with Matchers {
  import LanguageDatabase._

  "LanguageDatabase" should "open edict correctly" in {
    edict("少女") shouldBe "少女(P);乙女;小女 [しょうじょ(少女,小女)(P);おとめ(少女,乙女)] /(n) little girl/maiden/young lady/female usually between 7 and 18 years old/(P)/EntL1580290X/"
  }

  it should "open kanjidic correctly" in {
    kanjidic("少") shouldBe a [xml.Node]
  }

  it should "open kradfile correctly" in {
    kradfile("少") shouldBe "少 : ノ 小"
  }

  it should "open kanji diagrams correctly" in {
    diagrams.get("少") shouldBe a [Some[_]]
  }

}