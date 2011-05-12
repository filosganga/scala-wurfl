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

package org.ffdev.swurfl

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


  /**
   * Normalize the User-Agents containing the
   *   "Mozilla/4.0 (YesWAP mobile phone proxy)"
   * statement, removing it
   */
  val normalizeYesWapMobilePhoneProxy = (ua: String) => {
    ua.replace("Mozilla/4.0 (YesWAP mobile phone proxy)", "").trim
  }

  /**
   * Normalize the User-Agent containing the
   *   "(via babelfish.yahoo.com)"
   * statement, removing it
   */
  val normalizeBabelFish = (ua: String) => {
    ua.replace("(via babelfish.yahoo.com)", "").trim
  }

  /**
   * Normalize the User-Agent ending with the
   *   "UP.Link"
   * statement, removing from it
   */
  val normalizeUpLink = (ua: String) => {
    ua.split("UP.Link")(0).trim
  }

  /**
   * Normalize the User-Agent containing a serial number pattern:
   *   "/SNnnnn"
   * replacing numbers with sequence of "X" character.
   */
  private val vodafoneSnPattern = """/SN(\d+)\s""".r

  val normalizeVodafoneSn = (ua: String) => {

    vodafoneSnPattern.replaceAllIn(ua, m =>
      "/SN" + "X" * (m.end(1) - m.start(1)) + " "
    )
  }

}