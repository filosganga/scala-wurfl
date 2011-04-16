package org.filippodeluca.swurfl.repository.xml

import org.specs.Specification
import org.ffdev.swurfl.repository.xml.XmlResource


class XmlResourceSpec extends Specification {

  "XmlResource" should {
    "parse" in {
      "return no empty data" in {
        new XmlResource("classpath:///root.xml").devices.isEmpty must beFalse
      }
      "return one root at least" in {
        new XmlResource("classpath:///root.xml").devices.count(_.isRoot) must greaterThan(0)
      }
    }
  }
}