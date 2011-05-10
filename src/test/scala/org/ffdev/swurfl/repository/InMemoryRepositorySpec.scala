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

package org.ffdev.swurfl.repository

import org.specs.Specification
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