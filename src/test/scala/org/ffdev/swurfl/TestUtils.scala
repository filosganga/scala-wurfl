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

import io.Source


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