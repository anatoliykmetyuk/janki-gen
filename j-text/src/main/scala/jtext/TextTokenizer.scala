package jtext

import scala.collection.JavaConversions._
import scala.language.implicitConversions

import org.atilika.kuromoji._;

import jentities._
import jentities.util.LanguageDatabase

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

  /**
   * Path an arbitrary Japanese text string to this method
   * and it will extract all the words from it.
   */
  def apply(text: String)(implicit ldb: LanguageDatabase): Seq[Vocabulary] = {
    val tokenizer   = Tokenizer.builder.build()
    val refinedText = text.filter(_ != '\n')

    tokenizer.tokenize(refinedText)
      .filter {t => allowedPartsOfSpeech(t.getPartOfSpeech.split(",").head)}
      .map    {t => Vocabulary(t.getBaseForm)}
  }

}