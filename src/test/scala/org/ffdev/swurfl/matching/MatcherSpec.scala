package org.filippodeluca.swurfl.matching


import org.ffdev.swurfl.matching.Matcher
import org.ffdev.swurfl.Headers



class MatcherSpec {

//  @Test
//  def testCommonPrefixUserAgents() {
//
//    val matcher = new Matcher() {}
//
//    val expected = "a"
//    val userAgent = "Nokia8800 (4.57) Bla Bla Bla"
//
//    val definitions = Seq(
//      new DummyDeviceEnty("a", "Nokia8800 (4.56) Bla Bla Bla"),
//      new DummyDeviceEnty("b", "Nokia8700 (4.56) Bla Bla Bla")
//    )
//    matcher.init(definitions)
//
//    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))
//    assertEquals(expected, response)
//  }
//
//  @Test
//  def testLongerDifferentPrefixUserAgents() {
//
//    val matcher = new Matcher() {}
//
//    val expected = "a"
//    val userAgent = "Nokia8800 (4.56) Bla Bla Bla"
//
//    val definitions = Seq(
//      new DummyDeviceEnty("a", "Nokia8800 (4.567) Bla Bla Bla"),
//      new DummyDeviceEnty("b", "Nokia8700 (4.56) Bla Bla Bla")
//    )
//
//    matcher.init(definitions)
//    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))
//
//    assertEquals(expected, response)
//  }
//
//  @Test
//  def testLongerCommonPrefixUserAgents() {
//
//    val matcher = new Matcher() {}
//
//    val expected = "a"
//    val userAgent = "Nokia8800 (4.56) Topolino"
//
//    val definitions = Seq(
//      new DummyDeviceEnty("a", "Nokia8800 (4.567) Topolino"),
//      new DummyDeviceEnty("b", "Nokia8800 (4.56) Topolone")
//    )
//    matcher.init(definitions)
//
//    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))
//
//    assertEquals(expected, response)
//  }
//
//  @Test
//  def testDifferentInexistentPrefixUserAgents() {
//
//    val matcher = new Matcher() {}
//
//    val expected = "a"
//    val userAgent = "Mozilla/5 (13.24) Nokia8800 Bla Bla Bla"
//
//    val definitions = Seq(
//      new DummyDeviceEnty("a", "Mozilla/5 (12.34) Nokia8800 Bla Bla Bla"),
//      new DummyDeviceEnty("b", "Mozilla/5 (14.56) Nokia6600 Bla Bla Bla")
//    )
//    matcher.init(definitions)
//
//    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))
//
//    assertEquals(expected, response)
//  }
//
//  @Test
//  def testDifferentExistentPrefixUserAgents() {
//
//    val matcher = new Matcher() {}
//
//    val expected = "a"
//    val userAgent = "Mozilla/5 (13.24) Nokia8800 Bla Bla Bla"
//
//    val definitions = Seq(
//      new DummyDeviceEnty("a", "Mozilla/5 (12.34) Nokia8800 Bla Bla Bla"),
//      new DummyDeviceEnty("b", "Mozilla/5 (13.24) Nokia6600 Bla Bla Bla")
//    )
//    matcher.init(definitions)
//
//    val response = matcher.deviceId(Headers("user-agent"->List(userAgent)))
//
//    assertEquals(expected, response)
//  }


}