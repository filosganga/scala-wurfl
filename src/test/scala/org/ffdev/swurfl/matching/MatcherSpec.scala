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
import org.specs2.mutable._


class MatcherSpec extends Specification {


  "Matcher" should {
    "normalize" in {
      "YesWapPhoneProxy" in {
        val normalized = normalizeYesWapMobilePhoneProxy("PippoBaudo Mozilla/4.0 (YesWAP mobile phone proxy)   ")
        normalized must beEqualTo("PippoBaudo")
      }
      "BabelFish" in {
        val normalized = normalizeBabelfish("PippoBaudo (via babelfish.yahoo.com)")
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
      "Mozilled BlackBerry" in {
        val normalized = normalizeMozilledBlackBerry("Mozilla bla bla bla BlackBerry8800")
        normalized must beEqualTo("BlackBerry8800")
      }
    }
  }


}