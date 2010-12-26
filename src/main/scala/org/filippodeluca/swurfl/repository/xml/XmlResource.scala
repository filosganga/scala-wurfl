package org.filippodeluca.swurfl.repository.xml

import java.net.URI
import java.io.InputStream
import scala.collection.mutable.{Set => MutableSet}
import javax.xml.parsers.SAXParserFactory

import org.filippodeluca.swurfl.{util, repository}
import util.Loggable
import repository.{Resource, ResourceData}
import io.Source

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

  // TODO use scala.io.Source?
  private def getInputStream: InputStream = {

    uri.getScheme.toLowerCase match {

      case "classpath" => {
        val path = uri.getPath
        getClass.getResourceAsStream(path)
      }

      case _ => throw new RuntimeException("URI: " + uri + " not found")
    }

  }

}