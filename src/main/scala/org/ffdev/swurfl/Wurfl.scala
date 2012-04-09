/*
 * Copyright 2012.
 *   Filippo De Luca <me@filippodeluca.com>
 *   Fantayeneh Asres Gizaw <fantayeneh@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ffdev.swurfl

import matching.Matcher
import repository.xml.XmlResource
import repository.{Repository, InMemoryRepository, Resource}

class Wurfl private(protected val repository: Repository, protected val eventListener: (Any, String) => Unit) extends Matcher {

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
    new Wurfl(patchedRepo, eventListener)
  }



}

object Wurfl {

  def apply(main: String): Builder = {
    new Builder(main)
  }

  class Builder(private val main: String) {

    private var patches: Seq[String] = Seq.empty

    private var eventListener: (Any, String) => Unit = (src: Any, event: String)=>Unit

    def build(): Wurfl = {
      val repository = new InMemoryRepository(new XmlResource(main).devices, patches.map(new XmlResource(_)).map(_.devices):_*)
      new Wurfl(repository, eventListener)
    }

    def withPatch(patch: String): Builder = {
      patches = patches :+ patch
      this
    }

    def withPatches(patch: String, others: String*): Builder = {
      patches = patches ++ (patch +: others)
      this
    }
  }
}

