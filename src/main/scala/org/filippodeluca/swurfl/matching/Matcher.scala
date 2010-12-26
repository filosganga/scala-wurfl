package org.filippodeluca.swurfl.matching

import scalaj.collection.Imports._
import org.ardverk.collection.{Trie, StringKeyAnalyzer, PatriciaTrie}


import org.filippodeluca.swurfl.Headers
import org.filippodeluca.swurfl.repository.Repository


/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 26/12/10
 * Time: 19.30
 * To change this template use File | Settings | File Templates.
 */

trait Matcher {

  protected val repository: Repository

  private val prefixTrie = createPrefixTrie()
  private val suffixTrie = createSuffixTrie()

  protected def deviceId(headers: Headers): String = {

    val userAgent = headers.userAgent.get

    val id = prefixTrie.get(userAgent)

    //  TODO search across nearst UAs
    //    if(id==null) {
    //
    //    val candidates = mutable.ListBuffer[(String,String)]()
    //    val cursor = new Cursor[String, String] {
    //      def select(entry: Entry[_ <: String, _ <: String])  = {
    //        candidates + (entry.getKey->entry.getValue)
    //
    //        // TODO implement a strategy
    //        Decision.EXIT
    //      }
    //    }
    //    trie.select(userAgent, cursor)
    //    candidates.head._2
    //
    //
    //    }

    id
  }

  private def createPrefixTrie(): Trie[String, String] = {

    val trie = new PatriciaTrie[String, String](StringKeyAnalyzer.INSTANCE)

    trie.putAll(repository.values.foldLeft(Map[String, String]()) {
      (map, d) => map + (d.userAgent -> d.id)
    }.asJava)

    trie
  }

  private def createSuffixTrie(): Trie[String, String] = {

    val trie = new PatriciaTrie[String, String](StringKeyAnalyzer.INSTANCE)

    trie.putAll(repository.values.foldLeft(Map[String, String]()) {
      (map, d) => map + (d.userAgent.reverse -> d.id)
    }.asJava)

    trie
  }


}