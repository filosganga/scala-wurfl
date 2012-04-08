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

import org.specs2.mutable._
import java.util.Date

/**
 * TODO Document...
 *
 * @author Filippo De Luca
 * @version 1.0 15/04/11/04/2011
 */
class WurflSpec extends Specification {

  "Wurfl" should {
    "create" in {
      "without patch" in {
        Wurfl("classpath:///root.xml").build() must be_!=(null)
      }
      "with one patch" in {
        Wurfl("classpath:///root.xml").withPatch("classpath:///add_device_patch.xml").build() must be_!=(null)
      }
      "with more patches" in {
        Wurfl("classpath:///root.xml").withPatches("classpath:///add_device_patch.xml", "classpath:///add_group_patch.xml").build() must be_!=(null)
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

        val start = new Date
        val errors = (Set.empty[(UserAgentEntry, String)] /: TestUtils.loadRequestDevicesFile){(s,u)=>

          val matched = wurfl.device(Headers("user-agent"->Seq(u.userAgent))).id
          if(u.ids.contains(matched)) {
            s
          }
          else {
            s + (u->matched)
          }
        }
        val stop = new Date
        val duration = stop.getTime - start.getTime

        // errors.foreach(error => warning("id: " + error._2 + " is not in: " + error._1.ids.mkString("[", ",", "]")))
        // warning("Test effective duration: " + duration + "ms")


        // 82 are the acceptable errors with the given file
        errors.size must beLessThanOrEqualTo(83)
      }
    }
  }

}