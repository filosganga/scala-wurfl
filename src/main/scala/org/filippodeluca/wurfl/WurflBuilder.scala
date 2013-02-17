package org.filippodeluca.wurfl

import com.typesafe.config.{ConfigFactory, Config}
import repository.InMemoryRepository
import repository.xml.XmlResource

import scala.collection.JavaConversions._

/**
 * 
 * @author Filippo De Luca
 */
class WurflBuilder(cfg: Config = ConfigFactory.load()) {

  private var config = cfg.withFallback(ConfigFactory.defaultReference()).getConfig("wurfl")

  private var main: String = config.getString("main")
  private var patches: Seq[String] = config.getStringList("patches")

  private var normalizers: Seq[String=>String] = Seq.empty

  private var eventListener: (Any, String) => Unit = (src: Any, event: String)=>Unit

  def build(): Wurfl = {

    val repository = new InMemoryRepository(new XmlResource(main).devices, patches.map(new XmlResource(_)).map(_.devices):_*)

    new Wurfl(repository, normalizers, eventListener)
  }

  def withPatch(patch: String): WurflBuilder = {
    patches = patches :+ patch
    this
  }

  def withPatches(patch: String, others: String*): WurflBuilder = {
    patches = patches ++ (patch +: others)
    this
  }

  def withConfig(cfg: Config): WurflBuilder = {
    this.config = config
    this
  }


}
