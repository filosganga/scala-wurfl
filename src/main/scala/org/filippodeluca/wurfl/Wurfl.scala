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

package org.filippodeluca.wurfl

import matching.Matcher
import repository.xml.XmlResource
import repository.{Repository, InMemoryRepository, Resource}
import com.typesafe.config.{ConfigFactory, Config}

class Wurfl private(protected val repository: Repository, protected val eventListener: (Any, String) => Unit) {

  private val matcher = new Matcher(repository)

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

  def device(headers: Headers): Device = matcher.device(headers)
}

object Wurfl {

  def apply(main: String): Builder = {
    new Builder(main)
  }

  def apply(main: String, patches: String*): Builder = {
    apply(main).withPatches(patches.head, patches.tail:_*)
  }

  def apply(config: Config, main: String, patches: String*): Builder = {
    apply(main).withConfig(config).withPatches(patches.head, patches.tail:_*)
  }

  class Builder(private val main: String) {

    private var config: Config = ConfigFactory.load()

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

    def withConfig(cfg: Config): Builder = {
      this.config = config
      this
    }
  }
}

