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

package org.filippodeluca.wurfl.repository.xml

import org.specs2.mutable._
import java.io.FileNotFoundException

class XmlResourceSpec extends Specification {

  "XmlResource" should {
    "parse" in {
      "return no empty data" in {
        new XmlResource("classpath:/root.xml").devices.isEmpty must beFalse
      }
      "return one root at least" in {
        new XmlResource("classpath:/root.xml").devices.count(_.isRoot) must greaterThan(0)
      }
    }
    "throw FileNotFoundException" in {
      new XmlResource("classpath:notExistent.xml").devices must throwA[FileNotFoundException]
    }
  }
}