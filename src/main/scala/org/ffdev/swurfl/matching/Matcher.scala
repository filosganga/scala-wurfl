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

package org.ffdev.swurfl.matching

import scala.collection.mutable

import scalaj.collection.Imports._
import java.util.Map.Entry
import org.ardverk.collection._

import org.ffdev.swurfl.repository.Repository
import org.ffdev.swurfl.{Device, Headers}

trait Matcher {

  protected def repository: Repository

  private val userAgentPrefixTrie = new PatriciaTrie[String, Device](StringKeyAnalyzer.INSTANCE)
  private val userAgentSuffixTrie = new PatriciaTrie[String, Device](StringKeyAnalyzer.INSTANCE)

  init(repository)

  def device(headers: Headers): Device = {
    // TODO find generic
    userAgentMatch(headers).getOrElse(repository.roots.head)
  }

  private val userAgentMatch: (Headers) => Option[Device] = (headers: Headers) => headers.userAgent match {
    case Some(userAgent) => {
      // Chain of responsibility
      (None.asInstanceOf[Option[Device]] /: Seq(perfectMatch, nearestMatch)){(r,m)=>
        if(r.isEmpty) m(userAgent) else r
      }
    }
    case None => None
  }

  private val perfectMatch: (String)=>Option[Device] = (userAgent: String) => userAgentPrefixTrie.get(userAgent) match {
    case null => None
    case matched => Some(matched)
  }

  // Add actors
  private val nearestMatch: (String)=>Option[Device] = (userAgent: String) => {

    // TODO Dummy tolerance
    var tolerance = (userAgent.length * 0.66).toInt


    val prefixes = Matcher.selectCandidates(userAgent, userAgentPrefixTrie)
    val suffixes = Matcher.selectCandidates(userAgent.reverse, userAgentSuffixTrie)
    val commons = prefixes.filterKeys(suffixes.contains(_))

    val candidates: Map[String, Device] = if (commons.isEmpty) prefixes else commons

    if(candidates.nonEmpty){
      Some(candidates.min(Ordering.by((e: (String, Device)) => Matcher.ld(userAgent, e._1)))._2)
    }
    else {
      None
    }

  }

  private def init(devices: Traversable[Device]) {

    val matchableDevices = devices.filter(_.userAgent.isDefined)

    userAgentPrefixTrie.clear()
    userAgentPrefixTrie.putAll(matchableDevices.map(d=>(d.userAgent.get->d)).toMap.asJava)

    userAgentSuffixTrie.clear()
    userAgentSuffixTrie.putAll(matchableDevices.map(d=>d.userAgent.get.reverse->d).toMap.asJava)
  }
}

object Matcher {

  private[Matcher] def selectCandidates(userAgent: String, trie: Trie[String, Device]): Map[String, Device] = {
    val cursor = new WurflCursor
    trie.select(userAgent, cursor)
    cursor.candidates.toMap
  }

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

  private class WurflCursor extends Cursor[String, Device] {

    val candidates = mutable.Map[String, Device]()
    var minLenght = -1

    override def select(entry: Entry[_ <: String, _ <: Device]): Cursor.Decision = {

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


