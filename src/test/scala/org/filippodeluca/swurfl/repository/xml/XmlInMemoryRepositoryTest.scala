package org.filippodeluca.swurfl.repository.xml

import org.junit.Test
import org.junit.Assert._
import org.filippodeluca.swurfl.repository.{CapabilityNotDefinedException, InvalidHierarchyException, NullUserAgentException, NotUniqueUserAgentException}

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 30/12/10
 * Time: 20.59
 * To change this template use File | Settings | File Templates.
 */

@Test
class XmlInMemoryRepositoryTest {

  @Test(expected = classOf[NullUserAgentException])
  def createWithNullUserAgentShouldRaiseException() {

    val repository = new XmlInMemoryRepository("classpath:///root.xml", "classpath:///null_user_agent_patch.xml")
  }

  @Test(expected = classOf[NotUniqueUserAgentException])
  def createWithNotUniqueUserAgentShouldRaiseException() {

    val repository = new XmlInMemoryRepository("classpath:///root.xml", "classpath:///not_unique_user_agent_patch.xml")
  }

  @Test(expected = classOf[InvalidHierarchyException])
  def createWithNullFallBackShouldRaiseException() {

    val repository = new XmlInMemoryRepository("classpath:///root.xml", "classpath:///null_fallback_patch.xml")
  }

  @Test(expected = classOf[InvalidHierarchyException])
  def createWithCircularHierarchyShouldRaiseException() {

    val repository = new XmlInMemoryRepository("classpath:///root.xml", "classpath:///circular_hierarchy_patch.xml")
  }

  @Test(expected = classOf[CapabilityNotDefinedException])
  def createWithInexistentCapabilityPatchShouldRaiseException() {

    val repository = new XmlInMemoryRepository("classpath:///root.xml", "classpath:///inexistent_capability_patch.xml")
  }

  @Test
  def createWithAddCapabilityPatchShouldReturnCapability() {

    val repository = new XmlInMemoryRepository("classpath:///root.xml", "classpath:///add_capability_patch.xml")

    assertTrue(repository.generic.capabilities.contains("capability_i"))
  }

  @Test
  def createWithAddDevicePatchShouldReturnDeviceWithCapability() {

    val repository = new XmlInMemoryRepository("classpath:///root.xml", "classpath:///add_device_patch.xml")

    assertTrue(repository.get("device_d").exists((_.capabilities("capability_a") == "device_d_a")))
  }



}