/*
 * Copyright (c) 2011.
 *   Fantayeneh Asres Gizaw <fantayeneh@gmail.com>
 *   Filippo De Luca <me@filippodeluca.com>
 *
 * This file is part of swurfl.
 *
 * swurfl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * swurfl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with swurfl. If not, see <http://www.gnu.org/licenses/>.
 */

package org.ffdev.swurfl.matching


import org.ffdev.swurfl.{Wurfl, Device, Headers}
import org.ffdev.swurfl.matching.trie.PatriciaTrie

trait Matcher {
  this: Wurfl =>

  protected val normalizers: Iterable[(String)=>String] = Seq(
    normalizeUpLink, normalizeBabelFish, normalizeYesWapMobilePhoneProxy, normalizeVodafoneSn)

  private val userAgentPrefixTrie = new PatriciaTrie[String, Device]
  private val userAgentSuffixTrie = new PatriciaTrie[String, Device]

  init(repository)

  def device(headers: Headers): Device = {
    // TODO find generic
    userAgentMatch(headers).getOrElse(repository.roots.head)
  }

  private val userAgentMatch: (Headers) => Option[Device] = (headers: Headers) => headers.userAgent match {
    case Some(userAgent) => {
      val normalized = (userAgent /: normalizers){(ua,n)=>n(ua)}
      // Chain of responsibility
      (None.asInstanceOf[Option[Device]] /: Seq(perfectMatch, nearestMatch)){(r,m)=>
        if(r.isEmpty) m(normalized) else r
      }
    }
    case None => None
  }

  private val perfectMatch: (String)=>Option[Device] = (userAgent: String) => userAgentPrefixTrie.get(userAgent) match {
    case None => None
    case Some(matched) => {
      eventListener(this, "Device: " + matched.id + "matched by perfectMatch")
      Some(matched)
    }
  }

  // Add actors
  private val nearestMatch: (String)=>Option[Device] = (userAgent: String) => {

    val prefixes = userAgentPrefixTrie.searchCandidates(userAgent)
    val suffixes = userAgentSuffixTrie.searchCandidates(userAgent.reverse)

    val candidates: Seq[(String, Device)] = prefixes.filter(suffixes.contains(_)) match {
      case s if s.nonEmpty => s
      case _ => prefixes
    }


    if(candidates.nonEmpty){
      val matched = candidates.min(Ordering.by((e: (String, Device)) => ld(userAgent, e._1)))._2
      eventListener(this, "Device: " + matched.id + "matched by nearestMatch")
      Some(matched)
    }
    else {
      None
    }

  }

  private def init(devices: Traversable[Device]) {

    userAgentPrefixTrie.clear()
    userAgentSuffixTrie.clear()

    devices.foreach{d=>
      d.userAgent match {
        case None =>
        case Some(txt) => txt.trim match {
          case "root" =>
          case "" =>
          case userAgent => {

            val normalized = (userAgent /: normalizers){(ua,n)=>n(ua)}

            userAgentPrefixTrie += normalized->d
            userAgentSuffixTrie += normalized.reverse->d
          }
        }
      }
    }
  }
}



