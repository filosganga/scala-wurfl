package org.filippodeluca.swurfl.matching

import org.filippodeluca.swurfl.{Headers, Repository}

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 26/12/10
 * Time: 19.30
 * To change this template use File | Settings | File Templates.
 */

trait Matcher {

  private val repository: Repository

  protected val prefixTrie = createPrefixTrie()
  protected val suffixTrie = createSuffixTrie()

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



}