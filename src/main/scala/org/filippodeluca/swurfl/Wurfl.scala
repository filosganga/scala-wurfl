package org.filippodeluca.swurfl

import xml.XmlResource

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 11.18
 * To change this template use File | Settings | File Templates.
 */

trait Wurfl {

  protected val devices: Set[Device]
  protected val generic = devices.find(_.id == "generic").get

  def device(headers: Headers): Device = {

    val userAgent = headers.get("user-agent").head
    devices.find(_.userAgent == userAgent).getOrElse(generic)
  }

}

object Wurfl {

  class WurflBuilder(private val root : Resource, private val patches: Resource*) {

    def this(pt: String) {

      this(new XmlResource(pt))
    }

    def this(pt: String, pts: String*) {

      this(new XmlResource(pt), pts.map(new XmlResource(_)):_*)
    }

    def withPatch(pt: String): WurflBuilder = withPatch(new XmlResource(pt))

    def withPatch(pc: Resource): WurflBuilder = new WurflBuilder(root, patches + pc)

//    def withPatches(pcs: Resource*): WurflBuilder = new WurflBuilder(root, patches ++ pcs)
//
//    def withPatches(pts: String*): WurflBuilder = withPatches(pts.map(new XmlResource(_)))




    def build : Wurfl = {

      List

      new WurflImpl(root.parse.devices)
    }

  }

  def apply(root: Resource) : WurflBuilder = new WurflBuilder(root)

  def apply(rootPath: String) : WurflBuilder = new WurflBuilder(new XmlResource(rootPath))

}