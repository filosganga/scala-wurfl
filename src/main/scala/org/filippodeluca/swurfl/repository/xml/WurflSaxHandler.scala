package org.filippodeluca.swurfl.repository.xml

import collection.mutable
import mutable.{Set => MutableSet}
import org.xml.sax._
import helpers.DefaultHandler
import java.lang.String

import org.filippodeluca.swurfl.repository.DeviceDefinition

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 16.04
 * To change this template use File | Settings | File Templates.
 */
class WurflSaxHandler extends DefaultHandler {

  class DeviceEntry(val id: String, val userAgent: String, val fallBack: String, val isRoot: Boolean)

  private val _definitions: MutableSet[DeviceDefinition] = MutableSet()
  private var currentEntry: DeviceEntry = null;
  private var currentCapabilities: mutable.Map[String, String] = null

  def definitions : Set[DeviceDefinition] = {
    Set(_definitions.toList:_*)
  }

  override def endDocument = {}

  override def startDocument = {}

  override def endElement(uri : String, localName : String, qname : String) = qname match {
    case "device" => endDevice()
    case _ => /* Ignore other */
  }

  override def startElement(uri : String, localName : String, qname : String, attributes : Attributes) = qname match {

    case "device" => startDevice(attributes)
    case "capability" => startCapability(attributes)
    case _ => /* Ignore others */
  }



  protected def startCapability(attributes: Attributes) {
    currentCapabilities += attributes.getValue("name")->attributes.getValue("value")
  }

  protected def startDevice(attributes: Attributes) {

    currentEntry = deviceEntry(attributes)

    currentCapabilities = mutable.Map()
  }

  protected def endDevice() {


    val definition = new DeviceDefinition(
      currentEntry.id,
      currentEntry.userAgent,
      currentEntry.fallBack,
      currentEntry.isRoot,
      currentCapabilities.toMap
    )

    _definitions += definition

    currentEntry = null
    currentCapabilities = null

  }

  protected def deviceEntry(attributes: Attributes): DeviceEntry = {

    val id = attributes.getValue("id")
    val userAgent = attributes.getValue("user_agent")
    val fallBack = attributes.getValue("fall_back")
    val isRoot = attributes.getValue("actual_device_root") eq "true"

    new DeviceEntry(id, userAgent, fallBack, isRoot)
  }

}