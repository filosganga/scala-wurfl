package org.filippodeluca.swurfl.repository

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 26/12/10
 * Time: 19.17
 * To change this template use File | Settings | File Templates.
 */

trait Repository extends Map[String, DeviceDefinition] {

  def id: String

  def generic: DeviceDefinition

}