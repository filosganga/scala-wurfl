package org.filippodeluca.swurfl

import repository.xml.{XmlInMemoryRepository, XmlResource}
import repository.{Repository, Resource}

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 26/12/10
 * Time: 20.36
 * To change this template use File | Settings | File Templates.
 */

class WurflBuilder(private val root: Resource, private val patches: Seq[Resource]) {

  def this(rp: String, pts: Seq[String]) =
    this(new XmlResource(rp), pts.map(new XmlResource(_)))

  def withPatch(pc: Resource): WurflBuilder = new WurflBuilder(root, patches :+ pc)

  def withPatch(pt: String): WurflBuilder = withPatch(new XmlResource(pt))

  def build : Wurfl = new DefaultWurfl(new XmlInMemoryRepository(root, patches: _*))

  private class DefaultWurfl(protected val repository: Repository) extends Wurfl;

}

object WurflBuilder {

  def apply(root: Resource) : WurflBuilder = new WurflBuilder(root, Seq.empty)

  def apply(rp: String) : WurflBuilder = new WurflBuilder(rp, Seq.empty)

}



