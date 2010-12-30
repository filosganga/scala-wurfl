package org.filippodeluca.swurfl

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 30/12/10
 * Time: 22.19
 * To change this template use File | Settings | File Templates.
 */

class WurflException(val message: String = null, val cause: Throwable = null) extends Exception {

  def this(message: String) {
    this(message, null)
  }

  def this(cause: Throwable) {
    this(null, cause)
  }
}