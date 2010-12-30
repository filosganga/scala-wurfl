package org.filippodeluca.swurfl.repository.xml

import org.filippodeluca.swurfl.repository.{Resource, InMemoryRepository}

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 30/12/10
 * Time: 12.01
 * To change this template use File | Settings | File Templates.
 */

class XmlInMemoryRepository(root: Resource, patches: Resource*) extends InMemoryRepository(root.devices, patches.map(_.devices):_*) {

  def this(rootPath: String, patchPaths: String*) = {
    this(new XmlResource(rootPath), patchPaths.map(new XmlResource(_)):_*)
  }

  def patch(p: Resource) {
    patch(p.devices)
  }

  def reload(root: Resource, patches: Seq[Resource]) {
    reload(root.devices, patches.map(_.devices))
  }

}