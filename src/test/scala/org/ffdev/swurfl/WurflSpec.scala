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

        val errors = (Set.empty[(UserAgentEntry, String)] /: TestUtils.loadRequestDevicesFile){(s,u)=>

          val matched = wurfl.device(Headers("user-agent"->Seq(u.userAgent))).id
          if(u.ids.contains(matched)) {
            s
          }
          else {
            s + (u->matched)
          }
        }

        errors.foreach(error => warning("id: " + error._2 + " is not in: " + error._1.ids.mkString("[", ",", "]")))

        // 82 are the acceptable errors with the given file
        errors.size must beLessThanOrEqualTo(83)
      }
    }
  }

}