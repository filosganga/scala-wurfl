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

package org.scalawurfl.repository.xml

import org.scalawurfl.repository.{DeviceEntry, Resource}

import scala.collection.mutable
import java.net.{URL, URI}
import java.io.{FileNotFoundException, InputStream}

import org.xml.sax.helpers.XMLReaderFactory
import org.xml.sax.InputSource

import java.util.zip.{ZipInputStream, GZIPInputStream}

import resource._


class XmlResource(val uri: URI) extends Resource {

  def this(path: String) = this (URI.create(path))

  def devices: Traversable[DeviceEntry] = openInputStream(uri).fold(e=>throw new FileNotFoundException(uri.toString), input=> {

    val entries = mutable.Map.empty[String, DeviceEntry]

    val xmlReader = XMLReaderFactory.createXMLReader()
    xmlReader.setContentHandler(new WurflSaxHandler(entries))

    managed(input).acquireAndGet{x=>
      val inputSource = new InputSource(input)
      inputSource.setEncoding("UTF-8")

      xmlReader.parse(inputSource)
      entries.values
    }
  })

  private def openInputStream(uri: URI): Either[Exception, InputStream] = {

    try {
    val connection = urlFor(uri).openConnection()

    uri.getPath.substring(uri.getPath.lastIndexOf(".")) match {
      case ".gz" => {
        val zip = new ZipInputStream(connection.getInputStream)
        zip.getNextEntry
        Right(zip)
      }
      case ".zip" => Right(new GZIPInputStream(connection.getInputStream))
      case _ => Right(connection.getInputStream)
    }
    } catch {
      case e: Exception => Left(e)
    }
  }

  private def urlFor(uri: URI): URL = uri.getScheme match {
    case "classpath" => getClass.getResource(uri.getPath)
    case _ =>  uri.toURL
  }
}

object XmlResource {

  def apply(path: String) = new XmlResource(path)

  def apply(uri: URI) = new XmlResource(uri)
}