package org.filippodeluca.swurfl.repository.xml

import java.net.URI
import java.io.InputStream
import javax.xml.parsers.SAXParserFactory

import org.filippodeluca.swurfl.{repository, util}
import repository.{DeviceDefinition, Resource}
import util.Loggable

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 13.01
 * To change this template use File | Settings | File Templates.
 */
class XmlResource(val uri: URI) extends Resource with Loggable {

  def this(path: String) = this (URI.create(path))

  override def id: String = uri.toString

  override def devices: Traversable[DeviceDefinition] = {

    val handler = new WurflSaxHandler
    val parser = SAXParserFactory.newInstance.newSAXParser

    insideInputStream(parser.parse(_: InputStream, handler))

    handler.definitions
  }


  private def insideInputStream(f: InputStream => Unit) {

    var input: InputStream = null

    try {
      input = uri.getScheme.toLowerCase match {
        case "classpath" => getClass.getResourceAsStream(uri.getPath)
        case _ => throw new RuntimeException("URI: " + uri + " not found")
      }

      f(input)
    }
    finally {
      try{
        input.close()
      }
      catch {
        case _ => /* ignore errors */
      }
    }
  }

}