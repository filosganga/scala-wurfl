package org.filippodeluca.swurfl

import matching.Matcher
import repository.Repository
import util.Loggable


/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 11.18
 * To change this template use File | Settings | File Templates.
 */

trait Wurfl extends Matcher with Loggable {

  protected val repository: Repository

  init(repository.values)

  def deviceForHeaders(headers: Headers): Device = {
    device(deviceId(headers));
  }

  // TODO cache it
  def device(id: String): Device = {

    val definition = repository(id)
    val capabilities: Map[String, String] = repository.hierarchy(definition).foldRight(Map.empty[String, String])((d, m) => m ++ d.capabilities)

    new Device(definition.id, definition.userAgent, definition.isRoot, capabilities)
  }

}