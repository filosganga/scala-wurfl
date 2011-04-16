package org.ffdev.swurfl

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 19/12/10
 * Time: 20.41
 * To change this template use File | Settings | File Templates.
 */
trait Device {

  def id: String

  def userAgent: Option[String]

  def capability(name: String): Option[String]

  def capabilities: Map[String, String]

  def apply(name: String): String = capability(name).get
}