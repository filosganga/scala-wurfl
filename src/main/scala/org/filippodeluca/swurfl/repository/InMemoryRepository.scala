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

  private val definitions = createDefinitions(root, patches)

  override def id: String = "TBD"

  override val generic: DeviceDefinition = definitions("generic")

  override def -(key: String): Map[String, DeviceDefinition] = definitions - key

  override def iterator: Iterator[(String, DeviceDefinition)] = definitions.iterator

  override def get(key: String): Option[DeviceDefinition] = definitions.get(key)

  override def +[B1 >: DeviceDefinition](kv: (String, B1)): Map[String, B1] = definitions + kv

  private def createDefinitions(root: Resource, patches: Seq[Resource]): Map[String, DeviceDefinition] = {

    val rootData = root.parse
    rootData.devices.foldLeft(mutable.Map[String, DeviceDefinition]()){(map, d) => map += (d.id->d)}.toMap

  }

}