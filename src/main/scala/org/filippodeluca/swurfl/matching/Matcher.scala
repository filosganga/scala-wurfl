package org.filippodeluca.swurfl.matching

import org.filippodeluca.swurfl.{Headers, repository, util}
import repository.DeviceDefinition
import util.Loggable

import scalaj.collection.Imports._
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

  private val userAgentPrefixTrie = new PatriciaTrie[String, String](StringKeyAnalyzer.INSTANCE)
  private val userAgentSuffixTrie = new PatriciaTrie[String, String](StringKeyAnalyzer.INSTANCE)

  protected[matching] def deviceId(headers: Headers): String = {

    def userAgentMatch(headers: Headers): Option[String] = {

      def perfectMatch(userAgent: String): Option[String] = {

        // TODO use functional
        val userAgent = headers.userAgent.get

        val matched = userAgentPrefixTrie.get(userAgent)
        if(matched!=null)
          Some(matched)
        else
          None
      }

      // Add actors
      def nearestMatch(userAgent: String): Option[String] = {

        def selectCandidates(userAgent: String, trie: Trie[String, String]): Map[String, String] = {
          val cursor = new WurflCursor
          trie.select(userAgent, cursor)
          cursor.candidates.toMap
        }

        // TODO use functional
        val userAgent = headers.userAgent.get

        // TODO Dummy tolerance
        var tolerance = (userAgent.length * 0.66).toInt


        val prefixes = selectCandidates(userAgent, userAgentPrefixTrie)
        val suffixes = selectCandidates(userAgent.reverse, userAgentSuffixTrie)
        val commons = prefixes.filterKeys(suffixes.contains(_))

        val candidates = if(commons.isEmpty) prefixes else commons
        val best = candidates.min(Ordering.by((e: (String, String))=>Matcher.ld(userAgent, e._1)))

        Some(best._2)
      }

      headers.userAgent.flatMap({(userAgent)=>
        Seq(perfectMatch _, nearestMatch _).foldLeft[Option[String]](None)((r,f)=>if(r.isEmpty) f(userAgent) else r)
      })
    }

    // TODO TBD
    def uaProfMatch(headers: Headers): Option[String] = None

    val id = Seq(userAgentMatch _, uaProfMatch _).foldLeft[Option[String]](None){(m, f) =>
        if(m.isEmpty)
          f(headers)
        else
          m
    }

    // Return generic al least
    id.getOrElse("generic")
  }

  protected[matching] def init(devices: Traversable[DeviceDefinition]) {

    val matchableDevices = devices.filter(!_.userAgent.startsWith("DO_NOT_MATCH"));

    userAgentPrefixTrie.putAll(matchableDevices.foldLeft(Map[String, String]()) {
      (map, d) => map + (d.userAgent -> d.id)
    }.asJava)

    userAgentSuffixTrie.putAll(matchableDevices.foldLeft(Map[String, String]()) {
      (map, d) => map + (d.userAgent.reverse -> d.id)
    }.asJava)
  }

  class WurflCursor extends Cursor[String, String] {

    val candidates = mutable.Map[String, String]()
    var minLenght = -1

    override def select(entry: Entry[_ <: String, _ <: String]): Cursor.Decision = {

      if(minLenght<0)
        minLenght=StringKeyAnalyzer.INSTANCE.lengthInBits(entry.getKey)

      if(StringKeyAnalyzer.INSTANCE.lengthInBits(entry.getKey)<minLenght) {
        Cursor.Decision.EXIT
      }
      else {
        candidates += (entry.getKey->entry.getValue)
        Cursor.Decision.CONTINUE
      }
    }
  }

}

object Matcher {

  // FIXME To Fix ArrayOutOfBoundEx
  private[Matcher] def ld(s: String, t: String): Int = {

    val sl = s.length
    val tl = t.length

    if(sl==0) {
      tl
    }
    else if(tl==0) {
      sl
    }
    else {
      var pc = Array.range(0, sl + 1)

      for(j <- 1 to tl) {
        val tj = t(j - 1)
        val dst = Array.ofDim[Int](sl + 1)

        dst(0) = j

        for(i <- 1 to sl) {
          val cost = if(s(i-1) == tj) 0 else 1
          dst(i) = Seq(dst(i - 1) + 1, pc(i) + 1, pc(i-1) + cost).min
        }

        pc = dst
      }

      pc(sl)
    }
  }

}