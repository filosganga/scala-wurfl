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

package org.filippodeluca.wurfl

import org.specs2.mutable._


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

        headers.userAgent must beEqualTo(Some(userAgents(0)))
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

        headers.accept must beEqualTo(Some(accepts(0)))
      }
      "return None if not defined" in {
        val headers = Headers("User-Agent"->List("Mozilla"))
        headers.accept must beNone
      }

    }

  }

}