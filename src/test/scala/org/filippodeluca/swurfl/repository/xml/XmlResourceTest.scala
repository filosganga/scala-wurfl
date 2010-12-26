package org.filippodeluca.swurfl.repository.xml

import org.junit.Test
import org.junit.Assert._

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 13.11
 * To change this template use File | Settings | File Templates.
 */

@Test
class XmlResourceTest {

  @Test
  def parseShouldReturnNotEmptyData = {

    val resource = new XmlResource("classpath:///wurfl.xml");
    val data = resource.parse

    assertNotNull(data)
    assertFalse(data.devices.isEmpty)
  }

  @Test
  def parseShouldReturnDataWithGeneric = {

    val resource = new XmlResource("classpath:///wurfl.xml");
    val data = resource.parse

    assertTrue(data.devices.find(_.id == "generic").isDefined)
  }

  @Test
  def parseShouldReturnDataWithoutOrphanDevice = {

    val resource = new XmlResource("classpath:///wurfl.xml");
    val data = resource.parse

    assertTrue(data.devices.forall(device => device.fallBack != None || device.id == "generic"))
  }


}