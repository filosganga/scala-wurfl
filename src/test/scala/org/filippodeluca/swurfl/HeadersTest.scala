package org.filippodeluca.swurfl

import org.junit.{Test, Assert}
import Assert._

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 24/12/10
 * Time: 10.13
 * To change this template use File | Settings | File Templates.
 */

@Test
class HeadersTest {

  @Test
  def createWithSugar {

    val headers = Headers("user-agent"->List("Mozilla"),"accept"->List("text/html"))

    assertTrue(headers.contains("user-agent"))
    assertTrue(headers.get("user-agent").contains("Mozilla"))
  }

  @Test
  def keyShouldBeCaseinsensitive {

    val headers = Headers("User-Agent"->List("Mozilla"))

    assertTrue(headers.contains("user-agent"))
    assertTrue(headers.contains("useR-agenT"))
  }

  @Test
  def userAgentShouldReturnFirstString {

    val userAgents = List("One", "Two", "Three")

    val headers = Headers("user-agent"->userAgents)

    assertEquals(userAgents(0), headers.userAgent.get)
  }

  @Test
  def userAgentShouldReturnNoneIfNotDefined {

    val headers = Headers("Accept"->List("text/html"))

    assertEquals(None, headers.userAgent)
  }

    @Test
  def acceptShouldReturnFirstString {

    val accepts = List("One", "Two", "Three")

    val headers = Headers("accept"->accepts)

    assertEquals(accepts(0), headers.accept.get)
  }

  @Test
  def acceptShouldReturnNoneIfNotDefined {

    val headers = Headers("User-Agent"->List("Mozilla"))

    assertEquals(None, headers.accept)
  }

}