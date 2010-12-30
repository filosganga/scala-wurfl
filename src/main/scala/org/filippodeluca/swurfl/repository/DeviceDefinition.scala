package org.filippodeluca.swurfl.repository

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 09.22
 *
 * hierarchy is from fallBack to generic
 */
class DeviceDefinition(var id : String,
                       var userAgent : String,
                       val hierarchy : Iterable[String] = Seq.empty[String],
                       var isRoot : Boolean = false,
                       val capabilities : Map[String, String]) {

  def isGeneric = id == Repository.GENERIC

  def isNotGeneric = isGeneric == false

  override def toString =
    "DeviceDefinition[id: " + id + ", userAgent: " + userAgent + ", hierarchy: " + hierarchy + ", isRoot: " + isRoot + ", capabilities: " + capabilities + "]"

}