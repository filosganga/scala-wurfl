/*
 * Copyright 2011. Filippo De Luca
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

import org.specs.Specification

/**
 * Document... 
 *
 * @author: Filippo De Luca
 * @version: 1.0 15/04/11/04/2011
 */
class WurflSpec extends Specification {

  "Wurfl" should {
    "create" in {
      "without patch" in {
        Wurfl("classpath:///root.xml").build() must be_!=(null)
      }
      "with patches" in {
        Wurfl("classpath:///root.xml", "classpath:///add_device_patch.xml").build() must be_!=(null)
      }
    }
    "match device" in {
      "perfect match" in {
        val wurfl = Wurfl("classpath:///root.xml").build()
        wurfl.device(Headers("user-agent"->List("DEVICE A"))) must not(beNull)
      }
      "nearest match" in {
        val wurfl = Wurfl("classpath:///root.xml").build()
        wurfl.device(Headers("user-agent"->List("DEVICE"))) must not(beNull)
      }
      "real userAgent" in {
        val wurfl = Wurfl("classpath:///wurfl-regression.xml").build()

        TestUtils.loadRequestDevicesFile.forall{u =>
          wurfl.device(Headers("user-agent"->Seq(u.userAgent))).id must beEqual(u.id)
        }
      }
    }
  }

}