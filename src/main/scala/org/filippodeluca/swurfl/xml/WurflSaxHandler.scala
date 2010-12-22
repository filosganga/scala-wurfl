package org.filippodeluca.swurfl.xml

import scala.collection.mutable.{Set => MutableSet}
import org.xml.sax._
import helpers.DefaultHandler
import java.lang.String

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 16.04
 * To change this template use File | Settings | File Templates.
 */
class WurflSaxHandler extends DefaultHandler {

  private val _definitions : MutableSet[DeviceDefinition] = MutableSet()
  private var currentDefinition : DeviceDefinition = null;

  override def endDocument = {}

  override def startDocument = {}

  override def endElement(uri : String, localName : String, qname : String) = qname match {
    case "device" => _definitions += currentDefinition
    case _ => Unit
  }

  override def startElement(uri : String, localName : String, qname : String, attributes : Attributes) = qname match {

    case "device" => {

      val id = attributes.getValue("id")
      val userAgent = attributes.getValue("user_agent")
      val fallBack = attributes.getValue("fall_back")
      val isRoot = attributes.getValue("actual_device_root") eq "true"

      currentDefinition = new DeviceDefinition(id, userAgent)
      currentDefinition.fallBack = fallBack
      currentDefinition.isRoot = isRoot
    }

    case "capability" => {
      currentDefinition.capabilities += attributes.getValue("name")->attributes.getValue("value")
    }

    case _ => Unit

  }

  def definitions : Set[DeviceDefinition] = {
    Set(_definitions.toList:_*)
  }

}