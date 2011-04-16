package org.ffdev.swurfl

import matching.Matcher
import repository.xml.XmlResource
import repository.{InMemoryRepository, Resource}

private class Wurfl(main: Resource, patches: Resource*) {

  private val repository = new InMemoryRepository(main.devices, patches.map(_.devices):_*)
  private val matcher = new Matcher(repository)

  def this(main: String, patches: String*) = {
    this(new XmlResource(main), patches.map(new XmlResource(_)):_*)
  }

  def device(headers: Headers): Device = matcher.device(headers)

}