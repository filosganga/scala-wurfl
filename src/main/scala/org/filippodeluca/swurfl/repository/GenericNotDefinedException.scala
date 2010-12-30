package org.filippodeluca.swurfl.repository

import org.filippodeluca.swurfl.WurflException

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 30/12/10
 * Time: 22.54
 * To change this template use File | Settings | File Templates.
 */

// FIXME It is possible do not have dependency on the generic id?
class GenericNotDefinedException extends WurflException {

  override def getMessage = "Generic device is not defined"
}