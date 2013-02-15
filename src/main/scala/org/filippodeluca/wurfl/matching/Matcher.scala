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

package org.filippodeluca.wurfl.matching


import org.filippodeluca.wurfl.{Device, Headers}
import trie.{EmptyTrie, Trie}
import org.filippodeluca.wurfl.repository.Repository

class Matcher(repository: Repository) {

  protected def userAgentHeaders = Seq("X-OperaMini-Phone-UA", "X-Original-User-Agent", "X-Device-User-Agent", "Device-Stock-UA", "User-Agent")

  protected val normalizers: Iterable[Normalizer] = Seq(
    normalizeUpLink,
    normalizeBabelfish,
    normalizeYesWapMobilePhoneProxy,
    normalizeVodafoneSn,
    normalizeMozilledBlackBerry
  )

  protected val userAgentPrefixTrie: Trie[Device] = init(repository)

  def device(headers: Headers): Device = {
    // TODO find generic
    userAgentMatch(headers).getOrElse(repository.roots.head)
  }

  private val userAgentMatch: (Headers) => Option[Device] = (headers: Headers) => userAgent(headers) match {
    case Some(userAgent) => {
      val normalized = (userAgent /: normalizers){(ua,n)=>n(ua)}
      // Chain of responsibility
      Seq(perfectMatch, nearestMatch).view.map(x=> x(normalized)).collectFirst{
        case Some(x) =>x
      }
    }
    case None => None
  }

  private def userAgent(headers: Headers) = {
    userAgentHeaders.map(headers.get(_)).collectFirst{case xs if !xs.isEmpty => xs.head}
  }

  private val perfectMatch: (String)=>Option[Device] = (userAgent: String) => userAgentPrefixTrie.get(userAgent)

  private val nearestMatch: (String)=>Option[Device] = (userAgent: String) => {

    val candidates = Seq.empty[(String,Device)] // userAgentPrefixTrie.nearest(userAgent).toSeq

    if(candidates.nonEmpty){
      val matched = candidates.min(Ordering.by((e: (String, Device)) => ld(userAgent, e._1)))._2
      Some(matched)
    }
    else {
      None
    }
  }

  private def init(devices: Traversable[Device]): Trie[Device] = {

    devices.foldLeft(Trie.empty[Device]){(s,x)=>
      x.userAgent match {
        case None => s
        case Some("root") => s
        case Some("") => s
        case Some(userAgent) => {
          s + (normalize(userAgent)->x)
        }
      }
    }
  }

  private def normalize(s: String) = normalizers.foldLeft(s)((a,b)=>b(a))
}



