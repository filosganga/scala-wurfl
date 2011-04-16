package org.ffdev.swurfl.repository

import org.specs.Specification
import xml.XmlResource

/**
 * Document... 
 *
 * @author: Filippo De Luca
 * @version: 1.0 16/04/11/04/2011
 */

class InMemoryRepositorySpec extends Specification {

  "InMemoriRepository" should {

    "count devices" in {
      val repository = new InMemoryRepository(new XmlResource("classpath:///root.xml").devices)
      repository.count(_ => true) must beGreaterThan(0)
    }
    "traverse devices" in {
      val repository = new InMemoryRepository(new XmlResource("classpath:///root.xml").devices)
      repository.head must be_!=(null)
    }

  }


}