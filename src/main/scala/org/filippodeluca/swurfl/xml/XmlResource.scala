package org.filippodeluca.swurfl.xml

import java.net.URI
import xml.pull.{XMLEvent, EvElemStart, EvElemEnd, XMLEventReader}
import org.filippodeluca.swurfl.{Device, ResourceData, Resource}
import io.Source
import java.io.InputStream
import xml.{Text, Node}
import org.filippodeluca.swurfl.util.Loggable
import scala.collection.mutable.{Set => MutableSet}
import javax.xml.parsers.SAXParserFactory
import org.xml.sax._
import helpers.DefaultHandler
import java.lang.String
import util.logging.Logged

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 13.01
 * To change this template use File | Settings | File Templates.
 */
class XmlResource(val uri: URI) extends Resource with Loggable {

  def this(path: String) = this (URI.create(path))

  override def parse: ResourceData = {

    val input = getInputStream

    val handler = new WurflHandler
    val parser = SAXParserFactory.newInstance.newSAXParser

    parser.parse(input, handler, "UTF-8")

    input.close()

    val id = uri.toString
    val definitions = handler.getDefinitions

    logDebug("Parsed " + definitions.size + " definitions")





    new ResourceData(id, Set[Device]())
  }

  private def getInputStream: InputStream = {

    uri.getScheme.toLowerCase match {

      case "classpath" => {
        val path = uri.getPath
        getClass.getResourceAsStream(path)
      }

      case _ => throw new RuntimeException("URI: " + uri + " not found")
    }

  }

  class WurflHandler extends DefaultHandler with Loggable {

    private val definitions : MutableSet[DeviceDefinition] = MutableSet()
    private var currentDefinition : DeviceDefinition = null;

    override def endDocument = {}

    override def startDocument = {}

    override def endElement(uri : String, localName : String, qname : String) = {

      qname match {
        case "device" => definitions += currentDefinition
        case _ => Unit
      }
    }

    override def startElement(uri : String, localName : String, qname : String, attributes : Attributes) = {

      qname match {

        case "device" => {

          val id = attributes.getValue("id")
          val userAgent = attributes.getValue("user_agent")
          val fallBack = attributes.getValue("fall_back")
          val isRoot = attributes.getValue("actual_device_root") eq "true"

          currentDefinition = new DeviceDefinition(id, userAgent)
          currentDefinition.fallBack = if(fallBack !=null && !fallBack.isEmpty) Some(fallBack) else None
          currentDefinition.isRoot = isRoot
        }

        case "capability" => {
          currentDefinition.capabilities += attributes.getValue("name")->attributes.getValue("value")
        }

        case _ => Unit

      }

    }


    def getDefinitions : Set[DeviceDefinition] = {
      Set[DeviceDefinition]() ++ definitions
    }
  }

}