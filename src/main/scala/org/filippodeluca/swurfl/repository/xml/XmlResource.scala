package org.filippodeluca.swurfl.xml

import java.net.URI
import java.io.InputStream
import org.filippodeluca.swurfl.util.Loggable
import scala.collection.mutable.{Set => MutableSet}
import javax.xml.parsers.SAXParserFactory
import java.lang.String
import org.filippodeluca.swurfl._

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

    val handler = new WurflSaxHandler
    val parser = SAXParserFactory.newInstance.newSAXParser

    parser.parse(input, handler, "UTF-8")

    input.close()

    val id = uri.toString
    val definitions = handler.definitions

    logDebug("Parsed " + definitions.size + " definitions")

    //val devices = createDevices(definitions)
    //devices.ensuring(_.size == definitions.size)

    new ResourceData(id, definitions)
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


  def createDevices(definitions : Set[DeviceDefinition]) : Set[Device] = {

    import scala.collection.mutable.{Map => MutableMap}

    val definitionsById = definitions.foldLeft(Map[String, DeviceDefinition]()) { (m, d) => m(d.id) = d }
    val devicesById = MutableMap[String, Device]()

    def createDeviceIfDoesNotExist(id : String) : Device = {

      def createDevice(id : String) : Device = {

        val definition = definitionsById(id)
        val parent : Option[Device] =
          if(definition.fallBack=="root")
            None
          else
            Some(createDeviceIfDoesNotExist(definition.fallBack))


        // TODO I am not sure of this
        val deviceOwnProperties = Map(definition.capabilities.toIterator.toList:_*)
        val device = new Device(id, definition.userAgent, definition.isRoot, parent, deviceOwnProperties)
        devicesById += id->device

        device
      }

      devicesById.getOrElse(id, createDevice(id));
    }

    for(id <- definitionsById.keys) {
      createDeviceIfDoesNotExist(id)
    }


    val devices = Set[Device]()
    devices ++ devicesById.values
  }




}