package org.filippodeluca.wurfl.matching.trie

import org.specs2.mutable._

/**
 * 
 * @author Filippo De Luca
 */
class TrieSpec extends Specification {

  "Trie apply" should {
    "create Trie" in {
      val trie = EmptyTrie + ("TEST"->5)

      trie.get("TEST") must beSome
    }
  }

}
