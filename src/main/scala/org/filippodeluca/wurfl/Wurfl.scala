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

package org.filippodeluca.wurfl

import matching.trie.Trie
import matching.Strings.ld
import repository.{Repository, Resource}
import com.typesafe.config.{ConfigFactory, Config}

class Wurfl(
             repository: Repository,
             userAgentResolver: Headers=>Option[String],
             normalizers: Seq[String => String]) {

  protected val userAgentPrefixTrie: Trie[Device] = init(repository)

  def device(headers: Headers): Device = {
    // TODO find generic
    userAgentMatch(headers).getOrElse(repository.roots.head)
  }

  private val userAgentMatch: (Headers) => Option[Device] = (headers: Headers) => userAgentResolver(headers) match {
    case Some(userAgent) => {
      val normalized = (userAgent /: normalizers) {
        (ua, n) => n(ua)
      }
      // Chain of responsibility
      Seq(perfectMatch, nearestMatch).view.map(x => x(normalized)).collectFirst {
        case Some(x) => x
      }
    }
    case None => None
  }

  private val perfectMatch: (String) => Option[Device] = (userAgent: String) => {
    userAgentPrefixTrie.get(userAgent)
  }

  private val nearestMatch: (String) => Option[Device] = (userAgent: String) => {

    val candidates = userAgentPrefixTrie.nearest(userAgent).toMap.toSeq

    if (candidates.nonEmpty) {
      val matched = candidates.min(Ordering.by((e: (String, Device)) => ld(userAgent, e._1)))._2
      Some(matched)
    }
    else {
      None
    }
  }

  private def init(devices: Traversable[Device]): Trie[Device] = {

    (Trie.empty[Device] /: devices) {
      (s, x) =>
        x.userAgent match {
          case Some(userAgent) => {
            s + (normalize(userAgent) -> x)
          }
          case _ => s
        }
    }
  }

  private def normalize(s: String) = (s /: normalizers)((a, b) => b(a))

  private def patch(p: Resource, ps: Resource*): Wurfl = {
    val patchedRepo = repository.patch((p +: ps).map(_.devices): _*)
    new Wurfl(patchedRepo, userAgentResolver, normalizers)
  }

}

object Wurfl {

  def apply(config: Config = ConfigFactory.load()) = new WurflBuilder(config)

  def apply(main: String) = new WurflBuilder(ConfigFactory.load()).withMain(main)

}

