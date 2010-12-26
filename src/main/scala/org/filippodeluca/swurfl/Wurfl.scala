package org.filippodeluca.swurfl

import matching.Matcher
import repository.xml.XmlResource
import repository.{InMemoryRepository, Repository, Resource}
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
    if(id == repository.generic.id) {
      new Device(id, repository.generic.userAgent, repository.generic.isRoot, None, repository.generic.capabilities.toMap)
    }
    else {
      val definition = repository(id);
      new Device(definition.id, definition.userAgent, definition.isRoot, Some(device(definition.fallBack)), definition.capabilities.toMap)
    }

  }

}