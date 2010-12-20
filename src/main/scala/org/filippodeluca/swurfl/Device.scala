package org.filippodeluca.swurfl

import collection.MapLike

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 19/12/10
 * Time: 20.41
 * To change this template use File | Settings | File Templates.
 */
class Device(val id : String, val userAgent : String, val parent : Device, private val ownedProperties : Map[String, String]) {

  def get(key: String): String = ownedProperties.getOrElse(key, parent.get(key))

}