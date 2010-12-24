package org.filippodeluca.swurfl

import util.Loggable
import xml.XmlResource
import scalaj.collection.Imports._
import collection.mutable
import java.util.Map.Entry
import org.ardverk.collection.{Trie, Cursor, StringKeyAnalyzer, PatriciaTrie}
import Cursor.Decision

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 11.18
 * To change this template use File | Settings | File Templates.
 */

class Wurfl(protected val repository: Repository) extends Loggable {

  protected val prefixTrie = createPrefixTrie()
  protected val suffixTrie = createSuffixTrie()

  def device(headers: Headers): Device = {

    val userAgent = headers.userAgent.get


//    val candidates = mutable.ListBuffer[(String,String)]()
//    val cursor = new Cursor[String, String] {
//      def select(entry: Entry[_ <: String, _ <: String])  = {
//        candidates + (entry.getKey->entry.getValue)
//
//        // TODO implement a strategy
//        Decision.EXIT
//      }
//    }
//    trie.select(userAgent, cursor)
//    candidates.head._2

    val id = prefixTrie.get(userAgent)

    device(id)
  }

  // TODO cache it
  def device(id: String): Device = {
    if(id == repository.generic.id) {
      new Device(id, repository.generic.userAgent, repository.generic.isRoot, None, repository.generic.capabilities.toMap)
    }
    else {
      val definition = repository.definitions(id);
      new Device(definition.id, definition.userAgent, definition.isRoot, Some(device(definition.fallBack)), definition.capabilities.toMap)
    }

  }

  private def createPrefixTrie(): Trie[String, String] = {

    val trie = new PatriciaTrie[String, String](StringKeyAnalyzer.INSTANCE)

    trie.putAll(repository.definitions.values.foldLeft(Map[String,String]()){
      (map, d) => map + (d.userAgent->d.id)
    }.asJava)

    trie
  }

  private def createSuffixTrie(): Trie[String, String] = {

    val trie = new PatriciaTrie[String, String](StringKeyAnalyzer.INSTANCE)

    trie.putAll(repository.definitions.values.foldLeft(Map[String,String]()){
      (map, d) => map + (d.userAgent.reverse->d.id)
    }.asJava)

    trie
  }

}

object Wurfl {

  class WurflBuilder(private val root: Resource, private val patches: Seq[Resource]) {

    def this(rp: String, pts: Seq[String]) =
      this(new XmlResource(rp), pts.map(new XmlResource(_)))


    def withPatch(pc: Resource): WurflBuilder = new WurflBuilder(root, patches :+ pc)

    def withPatch(pt: String): WurflBuilder = withPatch(new XmlResource(pt))

    def build : Wurfl = {
      new Wurfl(new Repository(root, patches: _*))
    }

  }

  def apply(root: Resource) : WurflBuilder = new WurflBuilder(root, Seq.empty)

  def apply(rp: String) : WurflBuilder = new WurflBuilder(rp, Seq.empty)

}