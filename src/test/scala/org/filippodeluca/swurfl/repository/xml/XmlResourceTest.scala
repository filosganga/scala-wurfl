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

    assertFalse(resource.devices.isEmpty)
  }

  @Test
  def parseShouldReturnDataWithGeneric = {

    val resource = new XmlResource("classpath:///wurfl.xml");

    assertTrue(resource.devices.find(_.id == "generic").isDefined)
  }

  @Test
  def parseShouldReturnDataWithoutOrphanDevice = {

    val resource = new XmlResource("classpath:///wurfl.xml");

    assertTrue(resource.devices.forall(device => device.hierarchy != None || device.id == "generic"))
  }


}