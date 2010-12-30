package org.filippodeluca.swurfl

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 19/12/10
 * Time: 20.41
 * To change this template use File | Settings | File Templates.
 */
class Device(val id : String, val userAgent : String, val isRoot : Boolean, val properties : Map[String, String]) {


  override def hashCode: Int = {

    // TODO Boh...
    getClass.hashCode + id.hashCode + userAgent.hashCode + isRoot.hashCode + properties.hashCode
  }

  override def equals(o: Any): Boolean = {

    var eq = true

    if(o.isInstanceOf[Device]) {
      val oth = o.asInstanceOf[Device]

      eq = eq && id == oth.id
      eq = eq && userAgent == oth.userAgent
      eq = eq && isRoot == oth.isRoot
      eq = eq && properties == oth.properties
    }
    else {
      eq = false
    }

    eq
  }

  override def toString = "Device [id: " + id + "]"

}