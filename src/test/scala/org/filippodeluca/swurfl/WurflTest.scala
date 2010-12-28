package org.filippodeluca.swurfl

import org.junit.Test
import org.junit.Assert._
import io.Source
import util.Loggable
import java.io.{File, FileWriter}
import java.util.{Properties, Date}
import scalaj.collection.Imports._

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 22/12/10
 * Time: 15.31
 * To change this template use File | Settings | File Templates.
 */

@Test
class WurflTest extends Loggable {

  @Test
  def create {

    val wurfl = WurflBuilder("classpath:///root.xml").build
  }

  @Test
  def createWithOnePatch {

    val wurfl = WurflBuilder("classpath:///root.xml").withPatch("classpath:///add_device_patch.xml").build
    val deviceD = wurfl.device("device_d")

    assertNotNull(deviceD)
  }

  @Test
  def createWithMultiplePatches {

    val wurfl = WurflBuilder("classpath:///root.xml").withPatch("classpath:///add_device_patch.xml").withPatch("classpath:///add_cap_patch.xml").build
    val deviceD = wurfl.device("device_d")

    val capabilityI = deviceD.get("capability_i")

    assertNotNull(capabilityI)
  }

//  @Test
//  def matchDevicesShouldBeSmartEnough {
//
//    val wurfl = WurflBuilder("classpath:///wurfl.xml").build
//    val uas = Source.fromInputStream(getClass.getResourceAsStream("/handsets-ua-orig.txt"), "UTF-8").getLines
//
//    val start = new Date()
//    uas.foreach((ua: String) => wurfl.deviceForHeaders(Headers("user-agent"->List(ua))))
//    val end = new Date()
//
//    logInfo("Found multiple devices in " + (end.getTime - start.getTime) + "ms")
//  }

  @Test
  def matchDeviceShouldBeSmartEnough {

    val wurfl = WurflBuilder("classpath:///wurfl.xml").build


    val ua = "MOT-RAZRV3XXR_J/97.04.30R BER2.2 Mozilla/4.0 (compatible; MSIE 6.0; 678) Profile/MIDP-2.0 Configuration/CLDC-1.1  Opera 8.60 [en]"
    //val ua = "Nokia6"

    val start = new Date
    val id = wurfl.deviceForHeaders(Headers("user-agent"->List(ua)))
    val end = new Date

    logInfo("Found " + id + " in " + (end.getTime - start.getTime) + "ms")
  }

  @Test
  def matchShouldBeSameAsJava {

    val wurfl = WurflBuilder("classpath:///wurfl.xml").build

    val data = TestUtils.loadRequestDevicesFile


    data.foreach({
      (test) =>
        val device = wurfl.deviceForHeaders(Headers("user-agent"->List(test.userAgent)))
        assertEquals(test.userAgent + "Should match " + test.id + " instead of " + device.id, test.id, device.id)
    })

  }

}