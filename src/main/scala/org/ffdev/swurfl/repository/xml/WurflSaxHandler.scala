package org.ffdev.swurfl.repository.xml

import collection.mutable
import org.xml.sax._
import helpers.DefaultHandler
import org.ffdev.swurfl.repository.DeviceEntry

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 16.04
 * To change this template use File | Settings | File Templates.
 */
class WurflSaxHandler(entries: mutable.Map[String, DeviceEntry]) extends DefaultHandler {

  private var currentEntry: Option[DeviceEntry] = None
  private val capabilities = mutable.Map.empty[String, mutable.Map[String, String]]

  // SaxHandler **************************************************

  override def endDocument {}

  override def startDocument {}

  override def endElement(uri: String, localName: String, qname: String) = qname match {
    case "device" => endDevice()
    case _ => // Ignore others
  }

  override def startElement(uri: String, localName: String, qname: String, attributes: Attributes) = qname match {

    case "device" => startDevice(attributes)
    case "capability" => startCapability(attributes)
    case _ => // Ignore others
  }

  // Capability **************************************************

  private def startCapability(attributes: Attributes) {
    capabilityName(attributes.getValue("name").trim) match {
      case None => // Throw an exception?
      case Some(name) => addCapabilityIfValued(name, attributes)
    }
  }

  // It is a capability filter
  protected def capabilityName(name: String): Option[String] = Some(name)


  def addCapabilityIfValued(name: String, attributes: Attributes) {
    attributes.getValue("value").trim match {
      case "" =>
      case value => addCapability(name, value)
    }
  }

  def addCapability(name: String, value: String){
    currentEntry.foreach {entry =>
      capabilities(entry.id).+=(name -> value)
    }
  }

  // Device ******************************************************

  private def startDevice(attributes: Attributes){

    currentEntry = Some(new DeviceEntry(
      attributes.getValue("id"),
      parseUserAgent(attributes.getValue("user_agent")),
      parseFallback(attributes.getValue("fall_back")),
      Map.empty[String, String]
      )
    )

    currentEntry.foreach {entry =>
      capabilities += (entry.id -> mutable.Map.empty[String, String])
    }

  }

  private def parseFallback(value: String): Option[String] = value match {
    case "root" => None
    case v: String => stringOption(v)
  }

  private def parseUserAgent(value: String): Option[String] = value match {
    case "DO_NOT_MATCH" => None
    case v: String => stringOption(v)
  }

  private def stringOption(v: String): Option[String] = v match {
    case "" => None
    case x => Some(x)
  }

  private def endDevice() {
    val entry = currentEntry.get
    entries += (entry.id->new DeviceEntry(entry.id, entry.userAgent, entry.fallBackId, capabilities(entry.id).toMap))
    currentEntry = None
  }

}