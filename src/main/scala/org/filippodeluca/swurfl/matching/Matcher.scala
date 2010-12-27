package org.filippodeluca.swurfl.matching

import scalaj.collection.Imports._
import org.filippodeluca.swurfl.Headers
import org.filippodeluca.swurfl.repository.{DeviceDefinition, Repository}
import org.filippodeluca.swurfl.util.Loggable
import org.ardverk.collection.{Cursor, Trie, StringKeyAnalyzer, PatriciaTrie}
import java.util.Map.Entry
import scala.collection.mutable

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 26/12/10
 * Time: 19.30
 * To change this template use File | Settings | File Templates.
 */

trait Matcher extends Loggable {

  protected val repository: Repository

  private val prefixTrie = new PatriciaTrie[String, String](StringKeyAnalyzer.INSTANCE)
  private val suffixTrie = new PatriciaTrie[String, String](StringKeyAnalyzer.INSTANCE)

  protected def deviceId(headers: Headers): String = {

    def directMatch(headers: Headers): Option[String] = {

      // TODO use functional
      val userAgent = headers.userAgent.get

      val matched = prefixTrie.get(userAgent)
      if(matched!=null)
        Some(matched)
      else
        None
    }

    // Add actors
    def convergeMatch(headers: Headers): Option[String] = {

      // TODO use functional
      val userAgent = headers.userAgent.get

      //TODO search across nearst UAs with tollerance
      class matchingCursor extends Cursor[String, String] {

        val candidates = new mutable.ListBuffer[(String, String)]

        def select(entry: Entry[_ <: String, _ <: String]) = {
          candidates + (entry.getKey -> entry.getValue)

          // TODO implement a strategy
          Cursor.Decision.EXIT
        }
      }


      // TODO use actors
      val prefixCursor = new matchingCursor
      prefixTrie.select(userAgent, prefixCursor)

      val suffixCursor = new matchingCursor
      suffixTrie.select(userAgent, suffixCursor)


      val finalists = prefixCursor.candidates.intersect(suffixCursor.candidates)

      // TODO apply LD on finalists
      val head = finalists.headOption
      if(head.isDefined)
        Some(head.get._2)
      else
        None
    }

    // TBD
    def uaprofMatch(headers: Headers): Option[String] = None

    // TBD
    def safeMatch(headers: Headers): Option[String] = Some("generic")

    val methods = List[Headers => Option[String]](directMatch, convergeMatch, uaprofMatch, safeMatch)

    val id: Option[String] = methods.foldLeft[Option[String]](None){
      (matched, f) =>
        if(matched.isEmpty)
          f(headers)
        else
          matched
    }

    // For safe reasons
    id.getOrElse("generic")
  }

  protected def init(devices: Traversable[DeviceDefinition]) {

    prefixTrie.putAll(devices.foldLeft(Map[String, String]()) {
      (map, d) => map + (d.userAgent -> d.id)
    }.asJava)

    suffixTrie.putAll(devices.foldLeft(Map[String, String]()) {
      (map, d) => map + (d.userAgent.reverse -> d.id)
    }.asJava)

  }
}