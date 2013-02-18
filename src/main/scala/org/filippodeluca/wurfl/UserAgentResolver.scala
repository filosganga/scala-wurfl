package org.filippodeluca.wurfl

/**
 * 
 * @author Filippo De Luca
 */
trait UserAgentResolver {

  def resolve(headers: Headers): Option[String]

}

class DefaultUserAgentResolver(userAgentHeaders: Seq[String]) extends UserAgentResolver {

  def resolve(headers: Headers): Option[String] = {
    userAgentHeaders.map(headers.get(_)).collectFirst {
      case xs if !xs.isEmpty => xs.head
    }
  }
}