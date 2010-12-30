package org.filippodeluca.swurfl.repository

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 30/12/10
 * Time: 23.32
 * To change this template use File | Settings | File Templates.
 */

class CapabilityNotDefinedException(id: String, val capability: String) extends DeviceException(id) {

  override def getMessage = "Device " + id + " defines capability " + capability + " not defined by \"generic\"."
}