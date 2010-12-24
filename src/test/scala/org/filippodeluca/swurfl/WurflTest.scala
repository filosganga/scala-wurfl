package org.filippodeluca.swurfl

import org.junit.Test
import org.junit.Assert._
import io.Source
import java.util.Date
import util.Loggable

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


    val uas = Source.fromFile("/Users/filippodeluca/tmp/handsets-ua-orig.txt", "UTF-8").getLines

    val start = new Date()
    uas.foreach(ua => wurfl.device(Headers("user-agent"->List(ua))))
    val end = new Date()

    logInfo("Found " + uas.size + " uas in " + (end.getTime - start.getTime) + "ms")

  }

}