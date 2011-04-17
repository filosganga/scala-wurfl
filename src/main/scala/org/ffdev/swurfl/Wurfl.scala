package org.ffdev.swurfl

import matching.Matcher
import repository.xml.XmlResource
import repository.{Repository, InMemoryRepository, Resource}

class Wurfl private(protected val repository: Repository) extends Matcher {

  def patch(p: String): Wurfl = {
    patch(new XmlResource(p))
  }

  def patch(p: String, ps: String*): Wurfl = {
    patch(new XmlResource(p), ps.map(new XmlResource(_)):_*)
  }

  private def patch(p: Resource): Wurfl = {
    patch(p, Seq.empty[Resource]:_*)
  }

  private def patch(p: Resource, ps: Resource*): Wurfl = {
    val patchedRepo = repository.patch((p +: ps).map(_.devices):_*)
    new Wurfl(patchedRepo)
  }


}

object Wurfl {

  def apply(main: String, patches: String*): Builder = {
    new Builder(main, patches)
  }

  class Builder(val main: String, private var patches: Seq[String]) {

    def build(): Wurfl = {
      val repository = new InMemoryRepository(new XmlResource(main).devices, patches.map(new XmlResource(_)).map(_.devices):_*)
      new Wurfl(repository)
    }

    def withPatch(patch: String): Builder = {
      patches = patches :+ patch
      this
    }
  }
}

