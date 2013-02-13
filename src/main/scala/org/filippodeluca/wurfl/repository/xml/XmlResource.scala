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

package org.filippodeluca.wurfl.repository.xml

import scala.collection.mutable
import java.net.URI
import java.io.InputStream

import org.xml.sax.helpers.XMLReaderFactory
import org.xml.sax.InputSource

import org.filippodeluca.wurfl.repository.{DeviceEntry, Resource}


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
        case _: Throwable => // Ignore
      }
    }
  }


}