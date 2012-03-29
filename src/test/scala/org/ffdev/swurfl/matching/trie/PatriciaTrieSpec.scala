/*
 * Copyright 2011. ffdev.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ffdev.swurfl.matching.trie

import org.specs2.mutable._


class PatriciaTrieSpec extends Specification {


  "PatriciaTrie" should {
    "put and get value" in {
      val trie = new PatriciaTrie[String, Int]

      trie += "Foo"->23

      trie.size must beEqualTo(1)
      trie.get("Foo") must beEqualTo(Some(23))
      trie.get("Bar") must beNone
    }
    "put and get values" in {
      val trie = new PatriciaTrie[String, Int]

      trie += ("A"->1)
      trie += ("S"->2)
      trie += ("E"->3)
      trie += ("R"->4)
      trie += ("C"->5)
      trie += ("H"->6)

      trie.get("A") must beEqualTo(Some(1))
      trie.get("S") must beEqualTo(Some(2))
      trie.get("E") must beEqualTo(Some(3))
      trie.get("R") must beEqualTo(Some(4))
      trie.get("C") must beEqualTo(Some(5))
      trie.get("H") must beEqualTo(Some(6))
      trie.get("X") must beNone
      trie.size must beEqualTo(6)
    }
    "put and search values" in {
      val trie = new PatriciaTrie[String, Int]

      trie += ("A"->1)
      trie += ("AA"->2)
      trie += ("AB"->3)
      trie += ("ABA"->4)
      trie += ("ABB"->5)
      trie += ("ABC"->6)

      trie.search("ABD") must beEqualTo(Some(4))
    }
    "put and search candidates" in {
      val trie = new PatriciaTrie[String, Int]

      trie += ("A"->1)
      trie += ("AA"->2)
      trie += ("AB"->3)
      trie += ("ABA"->4)
      trie += ("ABB"->5)
      trie += ("ABC"->6)

      trie.searchCandidates("ABD") must beEqualTo(Seq("ABA"->4, "ABB"->5, "ABC"->6))
    }

  }

}