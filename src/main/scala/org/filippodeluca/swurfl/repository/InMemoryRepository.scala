package org.filippodeluca.swurfl.repository

import collection.immutable.Map
import collection.Iterator
import Repository._
import org.filippodeluca.swurfl.util.Loggable

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 24/12/10
 * Time: 15.02
 * To change this template use File | Settings | File Templates.
 */

class InMemoryRepository(roots: Traversable[DeviceDefinition], patches: Traversable[DeviceDefinition]*) extends Repository with Loggable {

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

    val generic = defs(GENERIC)

    if(!defs.get(GENERIC).exists(_.isGeneric)){
      // FIXME May we find root of the graph without use static id
      throw new GenericNotDefinedException
    }

    val defsValues = defs.values

    defsValues.find((d)=>d.isNotGeneric && (d.userAgent==null || d.userAgent.trim.isEmpty)).foreach{(d)=>
      throw new NullUserAgentException(d.id)
    }

    defsValues.find((d)=>d.isNotGeneric && d.hierarchy.isEmpty).foreach{(d)=>
      throw new InvalidHierarchyException(d.id, d.hierarchy)
    }

    defsValues.find((d)=>d.isNotGeneric && d.hierarchy.last != GENERIC).foreach{(d)=>
      throw new InvalidHierarchyException(d.id, d.hierarchy)
    }

    defsValues.foreach((d)=>d.capabilities.keys.find(!generic.capabilities.contains(_)).foreach((x)=>
      throw new CapabilityNotDefinedException(d.id, x)
    ))
  }

  private def patchDefinitions(defs: Map[String, DeviceDefinition], patchers: Traversable[DeviceDefinition]): Map[String, DeviceDefinition] = {

    def explicitHierarchy(d: DeviceDefinition): Iterable[String] = {
      if(d.isGeneric){
        Seq.empty[String]
      }
      else {
        defs.get(d.hierarchy.last) match {
          case None => throw new InvalidHierarchyException(d.id, d.hierarchy)
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

    def patchHierarchies(pr: DeviceDefinition): Traversable[DeviceDefinition] = {
      defs.values.filter((d)=>d.hierarchy.exists(_ == pr.id)).map{(d)=>
        val hierarchy = (d.hierarchy.takeWhile(_ != pr.id).toSeq :+ pr.id) ++ pr.hierarchy
        new DeviceDefinition(d.id, d.userAgent, hierarchy, d.isRoot, d.capabilities)
      }
    }

    def newDefinition(s: DeviceDefinition): DeviceDefinition = {

      val hierarchy = explicitHierarchy(s);

      new DeviceDefinition(s.id, s.userAgent, hierarchy, s.isRoot, s.capabilities)
    }

    val patchings: Traversable[DeviceDefinition] = patchers.foldLeft(Seq[DeviceDefinition]()){(ps, pr)=>
      ps ++ (defs.get(pr.id) match {
        case None => Seq(newDefinition(pr))
        case pg => pg.map(patchDefinition(pr, _)).get +: patchHierarchies(pr).toSeq
      })
    }

    // TODO verify
    patchings.foldLeft(defs)((d, p) => d + (p.id->p))

  }

  // Event handling

  private def changed(defs: Traversable[DeviceDefinition]){/* Does nothing at this time */}

}