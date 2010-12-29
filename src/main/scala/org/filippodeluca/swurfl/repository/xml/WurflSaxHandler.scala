package org.filippodeluca.swurfl.repository.xml

import collection.mutable
import org.xml.sax._
import helpers.DefaultHandler


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

  // default true
  private val includeCapability: String => Boolean = (name)=>true

  private val hierarchies: mutable.Map[String, Seq[String]] = mutable.Map()
  private val entries: mutable.Map[String, DeviceEntry] = mutable.Map()
  private val capabilities: mutable.Map[String, mutable.Map[String, String]] = mutable.Map()
  private var currentCapabilities: Option[mutable.Map[String, String]] = None;

  def definitions : Iterable[DeviceDefinition] = {
    entries.values.map({
      (e) =>

      val cps = capabilities(e.id).toMap
      val hierarchy = hierarchies(e.id)
      new DeviceDefinition(e.id, e.userAgent, hierarchy, e.isRoot, cps)
    })
  }

  override def endDocument {}

  override def startDocument {}

  override def endElement(uri : String, localName : String, qname : String) {}

  override def startElement(uri : String, localName : String, qname : String, attributes : Attributes) = qname match {

    case "device" => startDevice(attributes)
    case "capability" => startCapability(attributes)
    case _ => /* Ignore others */
  }

  protected def startCapability(attributes: Attributes) {

    val name = attributes.getValue("name");
    if(includeCapability(name)) {
      val value = attributes.getValue("value")
      currentCapabilities.foreach(_ += (name->value))
    }
  }

  protected def startDevice(attributes: Attributes) {

    val entry = deviceEntry(attributes)
    entries += (entry.id->entry)

    val hierarchy: Seq[String] = if(entry.id != "generic")
      entry.fallBack +: hierarchies.getOrElse(entry.fallBack, Seq[String]())
    else
      Seq.empty[String]

    // Add this hierarchy
    hierarchies += (entry.id->hierarchy)

    // Updates existent hierarchies
    hierarchies ++= hierarchies.filter(_._2.lastOption.exists(_ == entry.id)).mapValues(_ ++ hierarchy)

    capabilities += (entry.id->mutable.Map())
    currentCapabilities = Some(capabilities(entry.id))
  }

  protected def deviceEntry(attributes: Attributes): DeviceEntry = {

    val id = attributes.getValue("id")
    val userAgent = attributes.getValue("user_agent")
    val fallBack = attributes.getValue("fall_back")
    val isRoot = attributes.getValue("actual_device_root") eq "true"

    new DeviceEntry(id, userAgent, fallBack, isRoot)
  }

}