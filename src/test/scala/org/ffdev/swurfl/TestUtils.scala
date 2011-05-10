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

import io.Source

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 28/12/10
 * Time: 12.25
 * To change this template use File | Settings | File Templates.
 */


object TestUtils {

  def loadRequestDevicesFile: Iterable[UserAgentEntry] = {

    val lines = Source.fromInputStream(getClass.getResourceAsStream("/unit-test.yml")).getLines

    lines.foldLeft(List[UserAgentEntry]()) {
      (list, line) =>
        if (line != null && !line.isEmpty && !line.trim.startsWith("#")) {
          val tokens = line.split("=")
          val userAgent = tokens(0)

          val ids = tokens(1).split(",")

          list :+ new UserAgentEntry(userAgent, Set(ids:_*))
        }
        else {
          list
        }
    }
  }

}