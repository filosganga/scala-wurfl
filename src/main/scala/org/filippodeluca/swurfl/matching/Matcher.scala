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

      class matchingCursor extends Cursor[String, String] {

        val candidates = new mutable.ListBuffer[(String, String)]

        var threshold: Int = -1

        // It sucks but no better at this time in my head
        def select(entry: Entry[_ <: String, _ <: String]) = {

          val cua = entry.getKey
          val cid = entry.getValue

          if(threshold<0) {
            threshold = StringKeyAnalyzer.INSTANCE.bitIndex(userAgent, cua)
          }

          val bcl = StringKeyAnalyzer.INSTANCE.bitIndex(userAgent, cua)
          if(bcl<threshold || candidates.size>= 16) {
            Cursor.Decision.EXIT
          }
          else {
            candidates + (entry.getKey -> entry.getValue)
            Cursor.Decision.CONTINUE
          }
        }
      }


      // Process suffixes by actor
//      val caller = self
//      actor {
        val suffixCursor = new matchingCursor
        suffixTrie.select(userAgent.reverse, suffixCursor)
        val suffixCandidates = suffixCursor.candidates.map((entry) => entry._1.reverse->entry._2)
//        caller ! suffixCursor.candidates.map((entry) => entry._1.reverse->entry._2)
//      }

      val prefixCursor = new matchingCursor
      prefixTrie.select(userAgent, prefixCursor)
      val prefixCandidates = prefixCursor.candidates


//      val finalists = receive {
//        case suffixCandidates: Seq[(String, String)] => prefixCandidates.intersect(suffixCandidates)
//      }

      val finalists = prefixCandidates.intersect(suffixCandidates)


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

    val matchableDevices = devices.filter(!_.userAgent.startsWith("DO_NOT_MATCH"));

    prefixTrie.putAll(matchableDevices.foldLeft(Map[String, String]()) {
      (map, d) => map + (d.userAgent -> d.id)
    }.asJava)

    suffixTrie.putAll(matchableDevices.foldLeft(Map[String, String]()) {
      (map, d) => map + (d.userAgent.reverse -> d.id)
    }.asJava)

  }
}