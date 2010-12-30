package org.filippodeluca.swurfl.repository

import collection.immutable.Map
import collection.Iterator
import Repository._

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 24/12/10
 * Time: 15.02
 * To change this template use File | Settings | File Templates.
 */

class InMemoryRepository(roots: Traversable[DeviceDefinition], patches: Traversable[DeviceDefinition]*) extends Repository {

  private var definitions = createDefinitions(roots, patches)

  override def id: String = "TBD"

  override def generic: DeviceDefinition = definitions(GENERIC)

  override def -(key: String): Map[String, DeviceDefinition] = definitions - key

  override def iterator: Iterator[(String, DeviceDefinition)] = definitions.iterator

  override def get(key: String): Option[DeviceDefinition] = definitions.get(key)

  override def +[B1 >: DeviceDefinition](kv: (String, B1)): Map[String, B1] = definitions + kv

  def patch(pd: Traversable[DeviceDefinition]) {

    val patcheds = patchDefinitions(definitions, pd)

    verifyDefinitions(patcheds)
    definitions = patcheds
  }

  def reload(rd: Traversable[DeviceDefinition], pds: Seq[Traversable[DeviceDefinition]] = Seq.empty) {
    definitions = createDefinitions(rd, pds)
  }

  // Private methods

  private def createDefinitions(defs: Traversable[DeviceDefinition], pds: Seq[Traversable[DeviceDefinition]] = Seq.empty): Map[String, DeviceDefinition] = {
    var createds = defs.foldLeft(Map[String, DeviceDefinition]())((m,d)=>m + (d.id->d))
    createds = pds.foldLeft(createds){(c,pd)=>
      patchDefinitions(c, pd)
    }

    verifyDefinitions(createds)
    createds
  }

  private def verifyDefinitions(defs: Map[String, DeviceDefinition]) {

    if(!defs.exists((e)=>e._1 == GENERIC && e._2.isGeneric)){
      throw new RuntimeException("Definitions do not contain generic device")
    }
    else if(!defs.forall((e)=>e._2.id == GENERIC || e._2.hierarchy.last == GENERIC)) {
      throw new RuntimeException("Definitions contain orphan devices")
    }
  }

  private def patchDefinitions(defs: Map[String, DeviceDefinition], patchers: Traversable[DeviceDefinition]): Map[String, DeviceDefinition] = {

    def explicitHierarchy(d: DeviceDefinition): Iterable[String] = {
      if(d.isGeneric){
        Seq.empty[String]
      }
      else {
        defs.get(d.hierarchy.last) match {
          case None => throw new RuntimeException("Device: " + d.id + " has illegal hierarchy: " + d.hierarchy)
          case ancestor => ancestor.map(d.hierarchy ++ _.hierarchy).get
        }
      }
    }

    def patchDefinition(pr: DeviceDefinition, pg: DeviceDefinition): DeviceDefinition = {

      // Same defintion
      pr.ensuring(_.id == pg.id)

      val hierarchy = explicitHierarchy(pr);
      val capabilities = pg.capabilities ++ pr.capabilities

      new DeviceDefinition(pr.id, pr.userAgent, hierarchy, pr.isRoot, capabilities)
    }

    def newDefinition(s: DeviceDefinition): DeviceDefinition = {

      val hierarchy = explicitHierarchy(s);

      new DeviceDefinition(s.id, s.userAgent, hierarchy, s.isRoot, s.capabilities)
    }

    val patchings: Traversable[DeviceDefinition] = patchers.map{(pr)=>
      defs.get(pr.id) match {
        case None => newDefinition(pr)
        case pg => pg.map(patchDefinition(pr, _)).get
      }
    }

    // TODO verify
    patchings.foldLeft(defs)((d, p) => d + (p.id->p))

  }

  // Event handling

  private def changed(defs: Traversable[DeviceDefinition]){/* Does nothing at this time */}

}