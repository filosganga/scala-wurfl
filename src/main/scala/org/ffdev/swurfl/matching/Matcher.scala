/*
 * Copyright 2012.
 *   Filippo De Luca <me@filippodeluca.com>
 *   Fantayeneh Asres Gizaw <fantayeneh@gmail.com>
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


import org.ffdev.swurfl.{Wurfl, Device, Headers}
import org.ffdev.swurfl.matching.trie.PatriciaTrie

trait Matcher {
  this: Wurfl =>

  protected val normalizers: Iterable[Normalizer] = Seq(
    normalizeUpLink,
    normalizeBabelfish,
    normalizeYesWapMobilePhoneProxy,
    normalizeVodafoneSn,
    normalizeMozilledBlackBerry
  )

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
      (None.asInstanceOf[Option[Device]] /: Seq(perfectMatch, searchMatch)){(r,m)=>
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

  private val searchMatch: (String)=>Option[Device] = (ua: String) => userAgentPrefixTrie.search(ua) match {
    case None => None
    case Some(x) => {
      eventListener(this, "Device: " + x.id + " matched by searchMatch")
      Some(x)
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



