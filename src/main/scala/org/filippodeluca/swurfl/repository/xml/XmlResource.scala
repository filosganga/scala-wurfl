package org.filippodeluca.swurfl.repository.xml

import java.net.URI
import java.io.InputStream

import org.filippodeluca.swurfl.{repository, util}
import repository.{DeviceDefinition, Resource}
import util.Loggable
import org.xml.sax.helpers.XMLReaderFactory
import org.xml.sax.InputSource

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
    val xmlReader = XMLReaderFactory.createXMLReader()
    xmlReader.setContentHandler(handler)

    val inputStream = openInputStream(uri)

    using(inputStream) {
      val inputSource = new InputSource(inputStream)
      inputSource.setEncoding("UTF-8")

      xmlReader.parse(inputSource)
    }

    handler.definitions
  }

  private def openInputStream(uri: URI): InputStream = uri.getScheme match {
    case "classpath" => getClass.getResourceAsStream(uri.getPath)
    case _ => throw new RuntimeException("URI: " + uri + " not found")
  }

  private def using(closable: {def close(): Unit})(body: => Unit) {

    try{
      body
    }
    finally {
      try {
        closable.close()
      }
      catch {
        case _ => // Ignore
      }
    }
  }


}