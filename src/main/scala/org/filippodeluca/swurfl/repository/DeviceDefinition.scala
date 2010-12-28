package org.filippodeluca.swurfl.repository

import collection.mutable

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 09.22
 * To change this template use File | Settings | File Templates.
 */
class DeviceDefinition(var id : String,
                       var userAgent : String,
                       val fallBack : String = "root",
                       var isRoot : Boolean = false,
                       val capabilities : Map[String, String]) {

  override def toString =
    "DeviceDefinition[id: " + id + ", userAgent: " + userAgent + ", fallBack: " + fallBack + ", isRoot: " + isRoot + ", capabilities: " + capabilities + "]"

}