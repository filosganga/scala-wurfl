/*
 * Copyright 2012.
 *   Filippo De Luca <me@filippodeluca.com>
 *   Fantayeneh Asres Gizaw <fantayeneh@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ffdev.swurfl.repository.xml

import collection.mutable
import org.xml.sax._
import helpers.DefaultHandler
import org.ffdev.swurfl.repository.DeviceEntry

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