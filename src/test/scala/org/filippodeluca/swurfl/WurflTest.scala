package org.filippodeluca.swurfl

import org.junit.Test
import org.junit.Assert._

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 22/12/10
 * Time: 15.31
 * To change this template use File | Settings | File Templates.
 */

@Test
class WurflTest {

  @Test
  def create {

    val wurfl = Wurfl("classpath:///root.xml").build
  }

  @Test
  def createWithOnePatch {

    val wurfl = Wurfl("classpath:///root.xml").withPatch("classpath:///add_device_patch.xml").build
  }

  @Test
  def createWithMultiplePatches {

    val wurfl = Wurfl("classpath:///root.xml").withPatch("classpath:///add_device_patch.xml").withPatch("classpath:///add_cap_patch.xml").build
  }

  @Test
  def trieShouldBeSmartEnough {

    val wurfl = Wurfl("classpath:///wurfl.xml").build
    wurfl.device(Headers("user-agent"->List("MOT-RAZRV3XXR_J/97.04.30R BER2.2 Mozilla/4.0 (compatible; MSIE 6.0; 13003290) Profile/MIDP-2.0 Configuration/CLDC-1.1  Opera 8.60 [en]")))

  }

}