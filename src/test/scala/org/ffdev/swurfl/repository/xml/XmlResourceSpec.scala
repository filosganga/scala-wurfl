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

package org.filippodeluca.swurfl.repository.xml

import org.specs.Specification
import org.ffdev.swurfl.repository.xml.XmlResource


class XmlResourceSpec extends Specification {

  "XmlResource" should {
    "parse" in {
      "return no empty data" in {
        new XmlResource("classpath:///root.xml").devices.isEmpty must beFalse
      }
      "return one root at least" in {
        new XmlResource("classpath:///root.xml").devices.count(_.isRoot) must greaterThan(0)
      }
    }
  }
}