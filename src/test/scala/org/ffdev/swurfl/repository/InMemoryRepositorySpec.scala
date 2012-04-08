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

package org.ffdev.swurfl.repository

import org.specs2.mutable._
import xml.XmlResource

/**
 * Document... 
 *
 * @author: Filippo De Luca
 * @version: 1.0 16/04/11/04/2011
 */

class InMemoryRepositorySpec extends Specification {

  "InMemoriRepository" should {

    "count devices" in {
      val repository = new InMemoryRepository(new XmlResource("classpath:///root.xml").devices)
      repository.count(_ => true) must beGreaterThan(0)
    }
    "traverse devices" in {
      val repository = new InMemoryRepository(new XmlResource("classpath:///root.xml").devices)
      repository.head must be_!=(null)
    }

  }


}