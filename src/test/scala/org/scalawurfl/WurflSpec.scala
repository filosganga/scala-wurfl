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

package org.scalawurfl

import org.specs2.mutable._

import org.specs2.specification.Scope
import com.typesafe.config.ConfigFactory


/**
 * TODO Document...
 *
 * @author Filippo De Luca
 * @version 1.0 15/04/11/04/2011
 */
class WurflSpec extends Specification {

  trait Wurfls extends Scope {

    val regression = Wurfl(ConfigFactory.load("regression.conf")).build()
  }

  "Wurfl" should {
    "create" in {
      "with default configuration" in {

        val wurfl = Wurfl().build()

        val device = wurfl.device(Headers.userAgent("Nokia7210/1.0 (2.01) Profile/MIDP-1.0 Configuration/CLDC-1.0"))

        device must be_!=(null)
      }
    }
    "match regression user-agents" in new Wurfls {

      val start = System.currentTimeMillis()

      val errors = TestUtils.testEntries.foldLeft(Seq.empty[(Device, String, Seq[String])]){
        (s,x)=>

          regression.device(Headers("user-agent"->Seq(x._1))) match {
            case d if(x._2.contains(d.id)) => s
            case d => s :+ (d,x._1,x._2)
          }
      }

      val duration = System.currentTimeMillis() - start

      errors.foreach(error => println("id: " + error._1.id + " is not in: " + error._3.mkString("[", ",", "]") + " for user-agent: " + error._2))
      println("Test effective duration: " + duration + "ms")

      errors.size must beLessThanOrEqualTo(0)

    }
  }

}