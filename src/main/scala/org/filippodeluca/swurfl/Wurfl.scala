package org.filippodeluca.swurfl

import util.Loggable
import xml.XmlResource
import org.ardverk.collection.{StringKeyAnalyzer, PatriciaTrie}
import scalaj.collection.Imports._
import java.util.Date

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 11.18
 * To change this template use File | Settings | File Templates.
 */

class Wurfl(protected val devices: Set[DeviceDefinition]) extends Loggable{

  protected val generic = devices.find(_.id == "generic").get
  protected val trie = new PatriciaTrie[String, String](StringKeyAnalyzer.INSTANCE)

  trie.putAll(devices.foldLeft(Map[String,String]()){
    (map, d) => map + (d.userAgent->d.id)
  }.asJava)


  def device(headers: Headers): String = {

    val userAgent = headers.userAgent


    trie.get(userAgent.get)
  }

}

object Wurfl {

  class WurflBuilder(private val root: Resource, private val patches: Seq[Resource]) {

    def this(rp: String, pts: Seq[String]) =
      this(new XmlResource(rp), pts.map(new XmlResource(_)))


    def withPatch(pc: Resource): WurflBuilder = new WurflBuilder(root, patches :+ pc)

    def withPatch(pt: String): WurflBuilder = withPatch(new XmlResource(pt))

    def build : Wurfl = {

      new Wurfl(root.parse.devices)
    }

  }

  def apply(root: Resource) : WurflBuilder = new WurflBuilder(root, Seq.empty)

  def apply(rp: String) : WurflBuilder = new WurflBuilder(rp, Seq.empty)

}