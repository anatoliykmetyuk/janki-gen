package jentities.util

/**
 * Useful utility methods for the Japanese characters.
 */
object JCharactersUtils {

  def isHiragana(c: Char): Boolean = c >= 0x3040 && c <= 0x309F

  def isKatakana(c: Char): Boolean = c >= 0x30A0 && c <= 0x30FF

  def isKana(c: Char): Boolean = isKatakana(c) || isHiragana(c)

  def isKanji(c: Char): Boolean = c >= 0x4E00 && c <= 0x9FCC

}