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
  import WurflBuilder._

  private val config = cfg.withFallback(ConfigFactory.defaultReference()).getConfig("wurfl")

  private val main: String = config.getString("main")
  private val patches: Seq[String] = config.getStringList("patches")

  private val userAgentResolver: (Headers) => Option[String] = configureUserAgentResolver(config)

  private val normalizers: Seq[String=>String] = configureNormalizers(config)

  def build(): Wurfl = {

    val repository = new InMemoryRepository(
      new XmlResource(main).devices,
      patches.map(new XmlResource(_)).map(_.devices):_*
    )

    new Wurfl(repository, userAgentResolver, normalizers)
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
