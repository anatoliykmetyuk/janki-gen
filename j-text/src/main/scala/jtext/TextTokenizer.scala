package jtext

import scala.collection.JavaConversions._
import scala.language.implicitConversions

import org.atilika.kuromoji._;

import jentities._

object TextTokenizer {

  val allowedPartsOfSpeech = Set(
    "名詞",    // めいし      - noun
    "形容詞",  // けいようし   - i-adjective
    "動詞",    // どうし      - verb
//  "助動詞",  // じょうどうし - auxiliary verb
//  "助詞",    // じょし      - particle
    "副詞",    // ふくし      - adverb
    "連体詞"   // れんたいし   - pre-noun adjectival
  )

  def apply(text: String): Seq[Vocabulary] = {
    val tokenizer   = Tokenizer.builder.build()
    val refinedText = text.filter(_ != '\n')

    tokenizer.tokenize(refinedText)
      .filter {t => allowedPartsOfSpeech(t.getPartOfSpeech.split(",").head)}
      .map    {t => Vocabulary(t.getBaseForm)}
  }

}