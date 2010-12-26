package org.filippodeluca.swurfl.repository

import collection.immutable.Map
import collection.{Iterator, mutable}

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 24/12/10
 * Time: 15.02
 * To change this template use File | Settings | File Templates.
 */

class InMemoryRepository(root: Resource, patches: Resource*) extends Repository {

  val definitions = createDefinitions(root, patches)

  val generic: DeviceDefinition = definitions("generic")

  def get(id: String): Option[DeviceDefinition] = {
    definitions.get(id)
  }

  def -(key: String): Map[String, DeviceDefinition] = definitions - key

  def iterator: Iterator[(String, DeviceDefinition)] = definitions.iterator

  def +[B1 >: DeviceDefinition](kv: (String, B1)): Map[String, B1] = definitions + kv

  private def createDefinitions(root: Resource, patches: Seq[Resource]): Map[String, DeviceDefinition] = {

    val rootData = root.parse
    rootData.devices.foldLeft(mutable.Map[String, DeviceDefinition]()){(map, d) => map += (d.id->d)}.toMap

  }

}