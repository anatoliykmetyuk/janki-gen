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

  it should "have a correct decomposition - with all the transitive decompositions, in correct order" in {
    Radical("并").elements should contain theSameElementsInOrderAs "ノ十一干二".toRadicals
  }

}

trait LanguageDatabaseSpecHelpers {

  implicit class RefinedString(s: String) {
    def toRadicals: Seq[Radical] = s.map {c => Radical(c.toString)}
  }

}