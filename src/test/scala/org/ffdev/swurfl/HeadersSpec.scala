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

package org.filippodeluca.swurfl

import org.ffdev.swurfl.Headers
import org.specs.Specification


class HeadersSpec extends Specification {

  "Headers" should {
    "create with sugar" in {
      val headers = Headers("user-agent"->List("Mozilla"),"accept"->List("text/html"))
      headers.contains("user-agent") must beTrue
      headers.get("user-agent").contains("Mozilla") must beTrue
    }
    "be case insensitive" in {
      val headers = Headers("User-Agent"->List("Mozilla"))
      headers.contains("useR-Agent") must beTrue
    }
    "userAgent" in {
      "return first" in {

        val userAgents = List("One", "Two", "Three")
        val headers = Headers("user-agent"->userAgents)

        headers.userAgent must beEqual(Some(userAgents(0)))
      }
      "return None if not defined" in {

        val headers = Headers("Accept"->List("text/html"))
        headers.userAgent must beNone
      }
    }
    "accept" in {
      "return first" in {
        val accepts = List("One", "Two", "Three")
        val headers = Headers("accept"->accepts)

        headers.accept must beEqual(Some(accepts(0)))
      }
      "return None if not defined" in {
        val headers = Headers("User-Agent"->List("Mozilla"))
        headers.accept must beNone
      }

    }

  }

}