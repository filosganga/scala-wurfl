package org.scalawurfl.matching.trie

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
    "add common root entry" in new Tries {

      val nonEmpty = empty + ("FOO"->5) + ("FOOL"->3)

      nonEmpty.size must_==(2)
    }
    "add more common root entry" in new Tries {

      val nonEmpty = empty + ("FOO"->5) + ("FOL"->3)

      nonEmpty.size must_==(2)
    }
    "add more common root entry" in new Tries {

      val nonEmpty = empty + ("FOO"->5) + ("FOL"->3) + ("FOLLA"->6) + ("BAM"->1) + ("BAO"->7)

      nonEmpty.size must_==(5)
    }
    "get entry in" in {
      "one size Trie" in  {

        val trie = EmptyTrie + ("TEST"->5)
        trie("TEST") must be_==(5)
      }
      "two size Trie" in  {

        val trie = EmptyTrie + ("TEST"->5) + ("BAR"->3)
        trie("BAR") must be_==(3)
      }
      "complex Trie" in  {

        val trie = EmptyTrie + ("FOO"->5) + ("FOL"->3) + ("FOLLA"->6) + ("BAM"->1) + ("BAO"->7)

        trie("FOLLA") must be_==(6)
      }
      "complex Trie" in  {

        val trie = EmptyTrie + ("FOO"->5) + ("FOL"->3) + ("FOLLA"->6) + ("BAM"->1) + ("BAO"->7)

        trie("BAM") must be_==(1)
      }
    }
  }

  "toMap" should {
    "preserve entries" in {
      val trie = EmptyTrie + ("FOO"->5) + ("FOL"->3) + ("FOLLA"->6) + ("BAM"->1) + ("BAO"->7)

      val map = trie.toMap

      map must havePairs("FOO"->5, "FOL"->3, "FOLLA"->6, "BAM"->1, "BAO"->7)
    }
    "turn back to Trie" in {
      val trie = EmptyTrie + ("FOO"->5) + ("FOL"->3) + ("FOLLA"->6) + ("BAM"->1) + ("BAO"->7)

      Trie(trie.toMap.toSeq: _*) must be_==(trie)
    }
    "have the same size" in {
      val trie = EmptyTrie + ("FOO"->5) + ("FOL"->3) + ("FOLLA"->6) + ("BAM"->1) + ("BAO"->7)

      trie.toMap must haveSize(5)
    }
  }

  "getNearest" should {
    "return the longest common node" in {
      val trie = Trie("FOO"->5, "FOL"->3, "FOLLA"->6, "BAMBOO"->8)

      trie.nearest("FOLLETTO") must be_==(Trie("FOLLA"->6))
    }
    "return the longest common prefix node" in {
      val trie = Trie("FOO"->5, "FOLLIA"->3, "FOLLA"->6, "BAMBOO"->8)

      trie.nearest("FOLLETTO") must be_==(Trie("FOLLIA"->3, "FOLLA"->6))
    }
  }

  trait Tries extends Scope {
    val empty = EmptyTrie
  }

}
