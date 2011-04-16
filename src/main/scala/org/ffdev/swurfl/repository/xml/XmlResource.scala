package org.ffdev.swurfl.repository.xml

import scala.collection.mutable
import java.net.URI
import java.io.InputStream

import org.xml.sax.helpers.XMLReaderFactory
import org.xml.sax.InputSource

import org.ffdev.swurfl.repository.{DeviceEntry, Resource}


/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 13.01
 * To change this template use File | Settings | File Templates.
 */
class XmlResource(val uri: URI) extends Resource {

  def this(path: String) = this (URI.create(path))

  def devices: Traversable[DeviceEntry] = {

    val entries = mutable.Map.empty[String, DeviceEntry]

    val xmlReader = XMLReaderFactory.createXMLReader()
    xmlReader.setContentHandler(new WurflSaxHandler(entries))

    val inputStream = openInputStream(uri)

    using(inputStream) {
      val inputSource = new InputSource(inputStream)
      inputSource.setEncoding("UTF-8")

      xmlReader.parse(inputSource)
    }

    entries.values
  }


  private def openInputStream(uri: URI): InputStream = uri.getScheme match {
    case "classpath" => getClass.getResourceAsStream(uri.getPath)
    // TODO manage file zip gzip etc
    case _ => uri.toURL.openStream()
    //case _ => throw new RuntimeException("URI: " + uri + " not found")
  }

  private def using(closable: {def close(): Unit})(body: => Unit) {

    try {
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