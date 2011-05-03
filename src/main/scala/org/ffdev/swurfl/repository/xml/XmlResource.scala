/*
 * Copyright (c) 2011.
 *   Fantayeneh Asres Gizaw <fantayeneh@gmail.com>
 *   Filippo De Luca <me@filippodeluca.com>
 *
 * This file is part of swurfl.
 *
 * swurfl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * swurfl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with swurfl. If not, see <http://www.gnu.org/licenses/>.
 */

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