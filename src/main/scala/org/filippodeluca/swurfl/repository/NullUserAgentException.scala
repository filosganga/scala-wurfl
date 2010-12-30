package org.filippodeluca.swurfl.repository

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 30/12/10
 * Time: 22.08
 * To change this template use File | Settings | File Templates.
 */

class NullUserAgentException(id: String) extends DeviceException(id) {

  override def getMessage = "Device " + id + " has null user-agent declared."

}