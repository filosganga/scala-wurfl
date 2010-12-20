package org.filippodeluca.swurfl.xml

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
  def test = {

    val resource = new XmlResource("classpath:///wurfl.xml");
    val data = resource.parse


    assertNotNull(data)
  }


}