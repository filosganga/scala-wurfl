package org.filippodeluca.swurfl.matching

import collection.mutable
import actors.Actor._

import scalaj.collection.Imports._
import org.filippodeluca.swurfl.Headers
import org.filippodeluca.swurfl.repository.{DeviceDefinition, Repository}
import org.filippodeluca.swurfl.util.Loggable
import org.ardverk.collection.{Cursor, Trie, StringKeyAnalyzer, PatriciaTrie}
import java.util.Map.Entry

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 26/12/10
 * Time: 19.30
 * To change this template use File | Settings | File Templates.
 */

trait Matcher extends Loggable {

  protected val repository: Repository

  protected val userAgentTrie = new PatriciaTrie[String, String](StringKeyAnalyzer.INSTANCE)

  protected def deviceId(headers: Headers): String = {

    def perfectMatch(headers: Headers): Option[String] = {

      // TODO use functional
      val userAgent = headers.userAgent.get

      val matched = userAgentTrie.get(userAgent)
      if(matched!=null)
        Some(matched)
      else
        None
    }

    // Add actors
    def nearestMatch(headers: Headers): Option[String] = {

      // TODO use functional
      val userAgent = headers.userAgent.get

      val matched = userAgentTrie.select(userAgent)

      // TODO Apply a threasold
      if(matched!=null) {
        val mua = matched.getKey
        val mid = matched.getValue
        Some(mid)
      }
      else {
        None
      }
    }

    // TODO TBD
    def uaprofMatch(headers: Headers): Option[String] = None

    val methods = List[Headers => Option[String]](perfectMatch, nearestMatch, uaprofMatch)

    val id: Option[String] = methods.foldLeft[Option[String]](None){
      (matched, f) =>
        if(matched.isEmpty)
          f(headers)
        else
          matched
    }

    // Return generic al least
    id.getOrElse("generic")
  }

  protected def init(devices: Traversable[DeviceDefinition]) {

    val matchableDevices = devices.filter(!_.userAgent.startsWith("DO_NOT_MATCH"));

    userAgentTrie.putAll(matchableDevices.foldLeft(Map[String, String]()) {
      (map, d) => map + (d.userAgent -> d.id)
    }.asJava)

  }
}