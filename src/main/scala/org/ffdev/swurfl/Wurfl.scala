/*
 * Copyright (c) 2011.
 *   Fantayeneh Asres Gizaw <fantayeneh@gmail.com>
 *   Filippo De Luca <me@filippodeluca.com>
 *
 * This file is part of swurfl.
 *
 * swurfl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * swurfl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with swurfl. If not, see <http://www.gnu.org/licenses/>.
 */

package org.ffdev.swurfl

import matching.Matcher
import repository.xml.XmlResource
import repository.{Repository, InMemoryRepository, Resource}

class Wurfl private(protected val repository: Repository) extends Matcher {

  protected var eventListener: (Any, String) => Unit = (source, event) => Unit

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

