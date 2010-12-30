package org.filippodeluca.swurfl.repository

import org.filippodeluca.swurfl.WurflException

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 30/12/10
 * Time: 22.35
 * To change this template use File | Settings | File Templates.
 */

class InvalidHierarchyException(id:String, val hierarchy: Iterable[String]) extends DeviceException(id) {

  override def getMessage = "Device " + id + " has invalid hierarchy: (" + hierarchy.mkString(", ") + ")"
}