package org.filippodeluca.swurfl.parser

import collection.immutable.HashSet
import scala.io._
import java.io.{InputStream, Reader}
import xml.pull._
import xml.{NamespaceBinding, MetaData}
import org.filippodeluca.swurfl.Device

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 02.06
 * To change this template use File | Settings | File Templates.
 */

class Parser {

  def parse(input : InputStream) : Set[Device] = {

    val source : Source = Source.fromInputStream(input, "UTF-8")

    val xmlParser : XMLEventReader = new XMLEventReader(source);
    xmlParser.foreach((event : XMLEvent) => {

      event match {
        case EvElemStart(prefix, "device", attributes, scope) => {

            val id = attributes.get("id")
            val userAgent = attributes.get("user_agent")
            val actualDeviceRoot = attributes.get("actual-device-root")
            val fallBack = attributes.get("fall_back")

            println("Start Device(" + id + ", " + userAgent + "," + fallBack + ")")

          }

        case EvElemStart(prefix, "group", attributes, scope) => println("Start group(" + attributes.get("id") + ")")
        case EvElemStart(prefix, "capability", attributes, scope) => println("Start capability(" + attributes.get("name") + ", " + attributes.get("value") + ")")
        //case EvElemEnd(prefix, "device") => ""
        case _ => Unit
      }

    })

    Set[Device]()

  }
}