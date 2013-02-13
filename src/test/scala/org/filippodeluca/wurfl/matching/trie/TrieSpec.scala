package org.filippodeluca.wurfl.matching.trie

import org.specs2.mutable._
import org.specs2.specification.Scope

/**
 * 
 * @author Filippo De Luca
 */
class TrieSpec extends Specification {

  "Trie" should {
    "create Trie" in {
      val trie = EmptyTrie + ("TEST"->5)

      trie.get("TEST") must beSome
    }
    "add entry" in new Tries {

      val nonEmpty = empty + ("FOO"->5) + ("BAR"->3)

      nonEmpty.size must_==(2)
    }
  }

  trait Tries extends Scope {
    val empty = EmptyTrie
  }

}
