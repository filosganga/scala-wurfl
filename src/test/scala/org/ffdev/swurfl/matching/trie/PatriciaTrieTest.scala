package org.filippodeluca.swurfl.matching.trie


import org.junit._
import org.junit.Assert._
import org.slf4j.LoggerFactory

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 16/01/11
 * Time: 18.03
 * To change this template use File | Settings | File Templates.
 */

class PatriciaTrieTest {

  private def newTrie = {

    val trie = new PatriciaTrie[Int]
    trie += ("pippo"->0)
    trie += ("pillola"->1)
    trie += ("pippa"->3)
    trie += ("palla"->4)

    trie

  }

  @Test
  def createShouldReturnNotNull {

    val trie = new PatriciaTrie[Int]

    assertNotNull(trie)
  }

  @Test
  def addShouldIncrementSize {

    val trie = new PatriciaTrie[Int]
    trie += ("pippo"->0)
    trie += ("pillola"->1)
    trie += ("pippa"->3)
    trie += ("palla"->4)

    assertEquals(4, trie.size)
  }

  @Test
  def containsShouldReturnTrueForAddedElement {

    val trie = new PatriciaTrie[Int]
    trie += ("pippo"->0)
    trie += ("pillola"->1)
    trie += ("pippa"->3)
    trie += ("palla"->4)

    assertTrue(trie.contains("pippo"))


  }

  @Test
  def getShouldReturnAddedElement {

    val trie = new PatriciaTrie[Int]
    trie += ("pippo"->0)
    trie += ("pillola"->1)
    trie += ("pippa"->3)
    trie += ("palla"->4)

    assertEquals(3, trie("pippa"))
  }

  @Test
  def toSeqShouldReturnAddedElements {

    val trie = new PatriciaTrie[Int]
    trie += ("pippo"->0)
    trie += ("pillola"->1)
    trie += ("pippa"->3)
    trie += ("palla"->4)

    LoggerFactory.getLogger(getClass).info("Elements: " + trie.toSeq)
  }
}