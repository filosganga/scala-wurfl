package org.filippodeluca.swurfl

import collection.mutable

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 24/12/10
 * Time: 15.02
 * To change this template use File | Settings | File Templates.
 */

class Repository(root: Resource, patches: Resource*) {

  val definitions = createDefinitions(root, patches)

  val generic: DeviceDefinition = definitions("generic")

  def get(id: String): Option[DeviceDefinition] = {
    definitions.get(id)
  }

  def size: Int = definitions.size

  private def createDefinitions(root: Resource, patches: Seq[Resource]): Map[String, DeviceDefinition] = {

    val rootData = root.parse
    rootData.devices.foldLeft(mutable.Map[String, DeviceDefinition]()){(map, d) => map += (d.id->d)}.toMap

  }

}