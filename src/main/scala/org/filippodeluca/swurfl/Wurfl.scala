package org.filippodeluca.swurfl

import util.Loggable
import xml.XmlResource
import scalaj.collection.Imports._
import org.ardverk.collection.{Trie, StringKeyAnalyzer, PatriciaTrie}
import org.filippodeluca.swurfl.matching.Matcher
import org.filippodeluca.swurfl.{Resource, Resource, Resource, Resource}

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 11.18
 * To change this template use File | Settings | File Templates.
 */

class Wurfl(protected val repository: Repository) extends Matcher with Loggable {

  protected val prefixTrie = createPrefixTrie()
  protected val suffixTrie = createSuffixTrie()

  def deviceForHeaders(headers: Headers): Device = {

    val deviceId = deviceId(headers);
    device(deviceId);
  }

  // TODO cache it
  def device(id: String): Device = {
    if(id == repository.generic.id) {
      new Device(id, repository.generic.userAgent, repository.generic.isRoot, None, repository.generic.capabilities.toMap)
    }
    else {
      val definition = repository(id);
      new Device(definition.id, definition.userAgent, definition.isRoot, Some(device(definition.fallBack)), definition.capabilities.toMap)
    }

  }

}

object Wurfl {

  class WurflBuilder(private val root: Resource, private val patches: Seq[Resource]) {

    def this(rp: String, pts: Seq[String]) =
      this(new XmlResource(rp), pts.map(new XmlResource(_)))


    def withPatch(pc: Resource): WurflBuilder = new WurflBuilder(root, patches :+ pc)

    def withPatch(pt: String): WurflBuilder = withPatch(new XmlResource(pt))

    def build : Wurfl = {
      new Wurfl(new InMemoryRepository(root, patches: _*))
    }

  }

  def apply(root: Resource) : WurflBuilder = new WurflBuilder(root, Seq.empty)

  def apply(rp: String) : WurflBuilder = new WurflBuilder(rp, Seq.empty)

}