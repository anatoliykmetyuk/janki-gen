package jentities

import org.scalatest._

class LanguageDatabaseSpec extends FlatSpec with Matchers with LanguageDatabaseSpecHelpers {

  "Radical" should "have a defined meanings" in {
    Radical("小").meanings should contain allOf ("little", "small")
    Radical("一").meanings should contain allOf ("one", "one radical (no.1)")
  }

  it should "have a diagram" in {
    Radical("小").diagram shouldBe a [Some[_]]
    Radical("小").diagram.get should include ("小")
  }

  it should "have a standard decomposition - with only the first-level elements" in {
    Radical("并").elements should contain theSameElementsInOrderAs "ノ干二".toRadicals
  }

  it should "have a full decomposition - with all the transitive decompositions, in correct order" in {
    Radical("并").allElements should contain theSameElementsInOrderAs "ノ十一干二".toRadicals
  }


  "Kanji" should "have a list of readings" in {
    Kanji("闇").readings should contain allOf ("アン", "オン", "やみ", "くら.い")
  }


  "Vocabulary" should "have a reading" in {
    Vocabulary("夢見る").reading shouldBe Some("ゆめみる")
  }

  it should "have a list of meanings" in {
    Vocabulary("夢判断").meanings should contain allOf(
      "(n) (1) interpretation of dreams/dream reading/oneirocriticism/oneiroscopy",
      "(2) The Interpretation of Dreams (book by Sigmund Freud, 1900)"
    )

    Vocabulary("蜜").meanings should contain allOf(
      "(n) (1) nectar",
      "(2) honey",
      "(3) honeydew",
      "(4) treacle/molasses",
      "(5) sorbitol (when visible as dark patches inside an apple)"
    )
  }

  it should "get decomposed to Kanjis properly" in {
    Vocabulary("夢のまた夢").elements should contain theSameElementsInOrderAs Seq(Kanji("夢"))
    Vocabulary("夢見る").elements should contain theSameElementsInOrderAs Seq(Kanji("夢"), Kanji("見"))
  }

}

trait LanguageDatabaseSpecHelpers {

  implicit class RefinedString(s: String) {
    def toRadicals: Seq[Radical] = s.map {c => Radical(c.toString)}
  }

}