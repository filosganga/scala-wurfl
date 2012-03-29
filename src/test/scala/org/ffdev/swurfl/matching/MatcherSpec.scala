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
import org.specs2.mutable._


class MatcherSpec extends Specification {


  "Matcher" should {
    "normalize" in {
      "YesWapPhoneProxy" in {
        val normalized = normalizeYesWapMobilePhoneProxy("PippoBaudo Mozilla/4.0 (YesWAP mobile phone proxy)   ")
        normalized must beEqualTo("PippoBaudo")
      }
      "BabelFish" in {
        val normalized = normalizeBabelFish("PippoBaudo (via babelfish.yahoo.com)   ")
        normalized must beEqualTo("PippoBaudo")
      }
      "UP.Link" in {
        val normalized = normalizeUpLink("PippoBaudo UP.Link bla bla bla")
        normalized must beEqualTo("PippoBaudo")
      }
      "Vodafone SN" in {
        val normalized = normalizeVodafoneSn("PippoBaudo /SN123456 bla bla bla")
        normalized must beEqualTo("PippoBaudo /SNXXXXXX bla bla bla")
      }
    }
  }





//  @Test
//  def testCommonPrefixUserAgents() {
//
//    val matcher = new Matcher() {}
//
//    val expected = "a"
//    val userAgent = "Nokia8800 (4.57) Bla Bla Bla"
//
//    val definitions = Seq(
//      new DummyDeviceEnty("a", "Nokia8800 (4.56) Bla Bla Bla"),
//      new DummyDeviceEnty("b", "Nokia8700 (4.56) Bla Bla Bla")
//    )
//    matcher.init(definitions)
//
//    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))
//    assertEquals(expected, response)
//  }
//
//  @Test
//  def testLongerDifferentPrefixUserAgents() {
//
//    val matcher = new Matcher() {}
//
//    val expected = "a"
//    val userAgent = "Nokia8800 (4.56) Bla Bla Bla"
//
//    val definitions = Seq(
//      new DummyDeviceEnty("a", "Nokia8800 (4.567) Bla Bla Bla"),
//      new DummyDeviceEnty("b", "Nokia8700 (4.56) Bla Bla Bla")
//    )
//
//    matcher.init(definitions)
//    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))
//
//    assertEquals(expected, response)
//  }
//
//  @Test
//  def testLongerCommonPrefixUserAgents() {
//
//    val matcher = new Matcher() {}
//
//    val expected = "a"
//    val userAgent = "Nokia8800 (4.56) Topolino"
//
//    val definitions = Seq(
//      new DummyDeviceEnty("a", "Nokia8800 (4.567) Topolino"),
//      new DummyDeviceEnty("b", "Nokia8800 (4.56) Topolone")
//    )
//    matcher.init(definitions)
//
//    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))
//
//    assertEquals(expected, response)
//  }
//
//  @Test
//  def testDifferentInexistentPrefixUserAgents() {
//
//    val matcher = new Matcher() {}
//
//    val expected = "a"
//    val userAgent = "Mozilla/5 (13.24) Nokia8800 Bla Bla Bla"
//
//    val definitions = Seq(
//      new DummyDeviceEnty("a", "Mozilla/5 (12.34) Nokia8800 Bla Bla Bla"),
//      new DummyDeviceEnty("b", "Mozilla/5 (14.56) Nokia6600 Bla Bla Bla")
//    )
//    matcher.init(definitions)
//
//    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))
//
//    assertEquals(expected, response)
//  }
//
//  @Test
//  def testDifferentExistentPrefixUserAgents() {
//
//    val matcher = new Matcher() {}
//
//    val expected = "a"
//    val userAgent = "Mozilla/5 (13.24) Nokia8800 Bla Bla Bla"
//
//    val definitions = Seq(
//      new DummyDeviceEnty("a", "Mozilla/5 (12.34) Nokia8800 Bla Bla Bla"),
//      new DummyDeviceEnty("b", "Mozilla/5 (13.24) Nokia6600 Bla Bla Bla")
//    )
//    matcher.init(definitions)
//
//    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))
//
//    assertEquals(expected, response)
//  }


}