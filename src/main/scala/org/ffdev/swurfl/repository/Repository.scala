package org.ffdev.swurfl.repository

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 26/12/10
 * Time: 19.17
 * To change this template use File | Settings | File Templates.
 */

import org.ffdev.swurfl.Device

trait Repository extends Traversable[Device] {

  def roots: Traversable[Device]

  def get(id: String): Option[Device]

  def apply(id: String): Device = get(id).get

  def patch(patches: Traversable[DeviceEntry]*): Repository

}

