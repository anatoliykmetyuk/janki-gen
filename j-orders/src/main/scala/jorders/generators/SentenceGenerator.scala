package jorders.generators

import jentities.util.LanguageDatabase
import jentities._

import jorders.Constants.Fields._
import scala.util.Random

trait SentenceGenerator extends Generator[Seq[String]] {

  def getSentences(seed: Set[String]): Seq[Sentence]

  def generate(payload: Seq[Entity], orderDetails: String, exclude: Seq[Entity] = Nil): Seq[String] = {
    val vocab: Seq[String] = payload
      .filter {e => e.isInstanceOf[Vocabulary] && !exclude.contains(e)}
      .map    {_.name}
    val orderTokens = orderDetails.split("\t")

    val count: Option[Int] = orderTokens.find(_.startsWith(s"$C=")).map {e =>
      val ptrn = s"""$C=(\\d+)""".r
      val ptrn(c: String) = e
      c.toInt
    }

    val allSents  = getSentences(vocab.toSet)
    val someSents = count match {
      case Some(c) => Random.shuffle(allSents).take(c)
      case None    => allSents
    }
    someSents.map {case Sentence(jap, eng) => s"$jap\t$eng"}
  }

}

class SentenceGeneratorExclusive(val ldb: LanguageDatabase) extends SentenceGenerator {

  def getSentences(seed: Set[String]): Seq[Sentence] = ldb.sentencesExclusive(seed)

}

class SentenceGeneratorInclusive(val ldb: LanguageDatabase) extends SentenceGenerator {

  def getSentences(seed: Set[String]): Seq[Sentence] = ldb.sentencesInclusive(seed)

}