package org.ffdev.swurfl.repository

/**
 * Document... 
 *
 * @author: Filippo De Luca
 * @version: 1.0 16/04/11/04/2011
 */

case class DeviceEntry(val id: String, val userAgent: Option[String] = None, val fallBackId: Option[String] = None, val capabilities: Map[String, String] = Map.empty){
  def isRoot = fallBackId.isEmpty
}