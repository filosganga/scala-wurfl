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
class WurflImplTest {

  @Test
  def create {

    val wurfl = Wurfl("classpath:///wurfl.xml").build
  }

  @Test
  def createWithOnePatch {

    val wurfl = Wurfl("classpath:///root.xml").withPatch("classpath:///patch_1.xml").build
  }

  @Test
  def createWithMultiplePatches {

    val wurfl = Wurfl("classpath:///root.xml").withPatch("classpath:///patch_1.xml").withPatch("classpath:///patch_2.xml").build
  }

  @Test
  def createWithMultipleAggregatePatches {

    val wurfl = Wurfl("classpath:///root.xml").withPatches("classpath:///patch_1.xml","classpath:///patch_2.xml").build
  }

}