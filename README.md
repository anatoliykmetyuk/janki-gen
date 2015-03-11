JAnki
====
JAnki is a library to simplify learning Japanese language with [Anki SRS](http://ankisrs.net/). It provides the means to analyze Japanese entities - words, kanjis and radicals - and generate Anki flashcards from them. The library consists of the following parts:

 - [JEntities](http://anki-japan.github.io/jentities/index.html#jentities.package) - defines 3 model classes - [Radical](http://anki-japan.github.io/jentities/index.html#jentities.Radical), [Kanji](http://anki-japan.github.io/jentities/index.html#jentities.Kanji) and [Vocabulary](http://anki-japan.github.io/jentities/index.html#jentities.Vocabulary) - which can be used to access the properties of these entities.
 - [JOrders](http://anki-japan.github.io/jorders/index.html#jorders.ProcessOrder$) - allows you to "order" your flashcards! Also has means to serialize them into a zip archive.
 - [JText](http://anki-japan.github.io/jtext/index.html#jtext.TextTokenizer$) - you take an arbitrary Japanese text, provide it to the means of this library and it extracts all the vocabulary out from it. As simple as a method invocation.

The **recommended approach for learning** is as follows:

 1. Take an arbitrary Japanese text
 2. Extract all the words from it, and all the Kanjis and Radicals from the words, into the flashcards
 3. Study Radicals, Kanjis and the vocabulary (in this sequence) using Anki
 4. Once remembered, go to the original text and read it. You already know the words used there, but you don't know the grammar - so go ahead and study any new grammar you encounter.

For more information about the **programmatic usage**, see the API.

Powered By
==========
JAnki uses the following projects:
- [Edict](http://www.edrdg.org/jmdict/edict.html)
- [Kanjidic2](http://www.edrdg.org/kanjidic/kanjd2index.html)
- [Kradfile](http://www.kanjicafe.com/kradfile_license.htm)
- [Kanji Colorize](https://github.com/cayennes/kanji-colorize)

Thank you!