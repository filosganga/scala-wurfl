package org.filippodeluca.swurfl.repository

import org.filippodeluca.swurfl.WurflException

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 30/12/10
 * Time: 22.30
 * To change this template use File | Settings | File Templates.
 */

class NotUniqueUserAgentException(id: String, val other: String) extends DeviceException(id) {

  override def getMessage = "Device " + id + " and " + other + " have the same user-agent declared"

}