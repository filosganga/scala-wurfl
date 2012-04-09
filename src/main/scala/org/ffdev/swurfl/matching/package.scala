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

package org.ffdev.swurfl

import scala.util.matching.Regex
import scala.util.matching.Regex.Match

package object matching {

  // FIXME To Fix ArrayOutOfBoundEx
  val ld = (s: String, t: String) => {

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

  type Normalizer = String=>String

  private val normalizeRegex = (r: Regex, replacer: Match => String)=>(ua: String) => r.replaceAllIn(ua, replacer).trim

  /**
   * Normalize the User-Agents containing the
   *   "Mozilla/4.0 (YesWAP mobile phone proxy)"
   * statement, removing it
   */
  val normalizeYesWapMobilePhoneProxy = normalizeRegex("""\s*Mozilla/4\.0 \(YesWAP mobile phone proxy\).*""".r, m=>"")

  /**
   * Normalize the User-Agent containing the
   *   "(via babelfish.yahoo.com)"
   * statement, removing it
   */
  val normalizeBabelfish = normalizeRegex("""\s*\(via babelfish.yahoo.com\)""".r, m=>"")

  /**
   * Normalize the User-Agent ending with the
   *   "UP.Link"
   * statement, removing from it
   */
  val normalizeUpLink = (ua: String) => ua match {
    case x if(x.contains("UP.Link")) => x.substring(0, x.indexOf("UP.Link")).trim
    case x => x
  }

  /**
   * Normalize the User-Agent containing a serial number pattern:
   *   "/SNnnnn"
   * replacing numbers with sequence of "X" character.
   */
  val normalizeVodafoneSn = normalizeRegex("""/SN(\d+)\s""".r, m=>"/SN" + "X" * (m.end(1) - m.start(1)) + " ")

  val normalizeMozilledBlackBerry = (ua: String) => ua match {
    case x if (x.contains("BlackBerry") && x.startsWith("Mozilla")) => x.substring(x.indexOf("BlackBerry")).trim
    case x => x
  }

}