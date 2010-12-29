package org.filippodeluca.swurfl.matching

import org.junit.Test
import org.junit.Assert._
import org.filippodeluca.swurfl.repository.DeviceDefinition
import org.filippodeluca.swurfl.Headers

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 29/12/10
 * Time: 12.48
 * To change this template use File | Settings | File Templates.
 */

@Test
class MatcherTest {

  @Test
  def testCommonPrefixUserAgents() {

    val matcher = new Matcher() {}

    val expected = "a"
    val userAgent = "Nokia8800 (4.57) Bla Bla Bla"

    val definition = Seq(
      new DeviceDefinition("a", "Nokia8800 (4.56) Bla Bla Bla", Seq.empty[String], true, Map[String, String]()),
      new DeviceDefinition("b", "Nokia8700 (4.56) Bla Bla Bla", Seq.empty[String], true, Map[String, String]())
    )
    matcher.init(definition)

    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))

    assertEquals(expected, response)
  }

  @Test
  def testLongerDifferentPrefixUserAgents() {

    val matcher = new Matcher() {}

    val expected = "a"
    val userAgent = "Nokia8800 (4.56) Bla Bla Bla"

    val definition = Seq(
      new DeviceDefinition("a", "Nokia8800 (4.567) Bla Bla Bla", Seq.empty[String], true, Map[String, String]()),
      new DeviceDefinition("b", "Nokia8700 (4.56) Bla Bla Bla", Seq.empty[String], true, Map[String, String]())
    )
    matcher.init(definition)

    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))

    assertEquals(expected, response)
  }

  @Test
  def testLongerCommonPrefixUserAgents() {

    val matcher = new Matcher() {}

    val expected = "a"
    val userAgent = "Nokia8800 (4.56) Topolino"

    val definition = Seq(
      new DeviceDefinition("a", "Nokia8800 (4.567) Topolino", Seq.empty[String], true, Map[String, String]()),
      new DeviceDefinition("b", "Nokia8800 (4.56) Topolone", Seq.empty[String], true, Map[String, String]())
    )
    matcher.init(definition)

    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))

    assertEquals(expected, response)
  }

  @Test
  def testDifferentInexistentPrefixUserAgents() {

    val matcher = new Matcher() {}

    val expected = "a"
    val userAgent = "Mozilla/5 (13.24) Nokia8800 Bla Bla Bla"

    val definition = Seq(
      new DeviceDefinition("a", "Mozilla/5 (12.34) Nokia8800 Bla Bla Bla", Seq.empty[String], true, Map[String, String]()),
      new DeviceDefinition("b", "Mozilla/5 (14.56) Nokia6600 Bla Bla Bla", Seq.empty[String], true, Map[String, String]())
    )
    matcher.init(definition)

    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))

    assertEquals(expected, response)
  }

  @Test
  def testDifferentExistentPrefixUserAgents() {

    val matcher = new Matcher() {}

    val expected = "a"
    val userAgent = "Mozilla/5 (13.24) Nokia8800 Bla Bla Bla"

    val definition = Seq(
      new DeviceDefinition("a", "Mozilla/5 (12.34) Nokia8800 Bla Bla Bla", Seq.empty[String], true, Map[String, String]()),
      new DeviceDefinition("b", "Mozilla/5 (13.24) Nokia6600 Bla Bla Bla", Seq.empty[String], true, Map[String, String]())
    )
    matcher.init(definition)

    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))

    assertEquals(expected, response)
  }


}