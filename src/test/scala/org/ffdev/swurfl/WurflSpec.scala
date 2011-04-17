package org.ffdev.swurfl

import org.specs.Specification

/**
 * Document... 
 *
 * @author: Filippo De Luca
 * @version: 1.0 15/04/11/04/2011
 */
class WurflSpec extends Specification {

  "Wurfl" should {
    "create" in {
      "without patch" in {
        Wurfl("classpath:///root.xml").build() must be_!=(null)
      }
      "with patches" in {
        Wurfl("classpath:///root.xml", "classpath:///add_device_patch.xml").build() must be_!=(null)
      }
    }
    "match device" in {
      "perfect match" in {
        val wurfl = Wurfl("classpath:///root.xml").build()
        wurfl.device(Headers("user-agent"->List("DEVICE A"))) must not(beNull)
      }
      "nearest match" in {
        val wurfl = Wurfl("classpath:///root.xml").build()
        wurfl.device(Headers("user-agent"->List("DEVICE"))) must not(beNull)
      }
      "real userAgent" in {
        val wurfl = Wurfl("classpath:///wurfl-regression.xml").build()

        TestUtils.loadRequestDevicesFile.forall{u =>
          wurfl.device(Headers("user-agent"->Seq(u.userAgent))).id must beEqual(u.id)
        }
      }
    }
  }

}