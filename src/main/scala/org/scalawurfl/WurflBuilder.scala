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

package org.scalawurfl

import repository.InMemoryRepository
import repository.xml.XmlResource

import com.typesafe.config.{ConfigFactory, Config}

import scala.collection.JavaConversions._


/**
 * 
 * @author Filippo De Luca
 */
class WurflBuilder(cfg: Config) {
  import WurflBuilder._

  private val config = cfg.withFallback(ConfigFactory.defaultReference()).getConfig("wurfl")

  protected var main: String = config.getString("main")
  protected var patches: Seq[String] = config.getStringList("patches")

  protected var userAgentResolver: (Headers) => Option[String] = configureUserAgentResolver(config)

  protected var normalizers: Seq[String=>String] = configureNormalizers(config)

  def build(): Wurfl = {

    val repository = new InMemoryRepository(
      new XmlResource(main).devices,
      patches.map(new XmlResource(_)).map(_.devices):_*
    )

    new Wurfl(repository, userAgentResolver, normalizers)
  }

  def withMain(x: String) = {
    this.main = x
    this
  }

  def withPatches(xs: String*) = {
    this.patches = this.patches ++ xs
    this
  }

  def withoutPatches = {
    this.patches = Seq.empty
    this
  }

  def withUserAgentResolver(x: Headers=>Option[String]) = {
    this.userAgentResolver = x
    this
  }

  def withNormalizers(xs: Seq[String=>String]) = {
    this.normalizers = this.normalizers ++ xs
    this
  }

  def withoutNormalizers = {
    this.normalizers = Seq.empty
    this
  }

}

object WurflBuilder {

  private def configureUserAgentResolver(config: Config): (Headers) => Option[String] = {
    val userAgentResolverConfig = config.getConfig("user-agent-resolver")

    val userAgentResolver = userAgentResolverConfig.getString("type") match {
      case "default" => {
        new DefaultUserAgentResolver(config.getStringList("user-agent-resolver.default.user-agent-headers"))
      }
      case other => throw new RuntimeException("user-agent-resolver [" + other + "] not supported at this time.")
    }

    val userAgentResolverFunction = userAgentResolver.resolve(_)
    userAgentResolverFunction
  }

  private def configureNormalizers(config: Config): Seq[String=>String] = {
    config.getStringList("user-agent-normalizers").map((x: String) =>
      Class.forName(x).newInstance.asInstanceOf[Normalizer]
    ).map(x=> x.normalize(_))
  }
}
