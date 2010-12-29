package org.filippodeluca.swurfl.repository

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 12.52
 * To change this template use File | Settings | File Templates.
 */

trait Resource {

  def id: String

  def devices : Traversable[DeviceDefinition]

}