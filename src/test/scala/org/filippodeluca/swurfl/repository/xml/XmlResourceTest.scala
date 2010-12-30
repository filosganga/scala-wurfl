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

    val resource = new XmlResource("classpath:///root.xml");

    assertFalse(resource.devices.isEmpty)
  }

  @Test
  def parseShouldReturnDataWithOneGeneric = {

    val resource = new XmlResource("classpath:///root.xml");

    assertTrue(resource.devices.count(_.isGeneric)==1)

  }

  @Test
  def parseShouldReturnDataWithoutOrphanDevice = {

    val resource = new XmlResource("classpath:///root.xml");

    assertTrue(resource.devices.forall(device => device.isGeneric || !device.hierarchy.isEmpty ))
  }


}