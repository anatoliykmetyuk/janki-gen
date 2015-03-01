package jentities.util

import org.scalatest._

class LanguageDatabaseSpec extends FlatSpec with Matchers {
  import LanguageDatabase._

  "LanguageDatabase" should "open edict correctly" in {
    edict("少女") shouldBe "少女(P);乙女;小女 [しょうじょ(少女,小女)(P);おとめ(少女,乙女)] /(n) little girl/maiden/young lady/female usually between 7 and 18 years old/(P)/EntL1580290X/"
  }

  it should "open kanjidic correctly" in {
    kanjidic("少") shouldBe "少 3E2F U5c11 B4 C42 G2 S4 F287 J4 N166 V1390 H3467 DP4278 DK2163 DL2915 L106 DN111 K231 O88 DO129 MN7475 MP4.0089 E143 IN144 DS93 DF47 DH160 DT95 DC153 DJ105 DB2.17 DG512 DM107 P4-4-4 I3n1.1 Q9020.0 DR1160 ZPP1-1-3 Yshao3 Yshao4 Wso ショウ すく.ない すこ.し {few} {little} "
  }

  it should "open kradfile correctly" in {
    kradfile("少") shouldBe "少 : ノ 小"
  }

  it should "open kanji diagrams correctly" in {
    diagrams.get("少") shouldBe a [Some[_]]
  }

}