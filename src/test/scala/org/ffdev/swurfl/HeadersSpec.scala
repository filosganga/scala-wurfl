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