package org.scalawurfl

import org.specs2.mutable.Specification
import org.specs2.specification.Scope

/**
 * 
 * @author Filippo De Luca
 */
class NormalizerSpec extends Specification {

  trait NormalizersScope extends Scope {

    val yesWap = new YesWapMobilePhoneNormalizer

    val babelFish = new BabelFishNormalizer

    val upLink = new UpLinkNormalizer

    val serialNumber = new SerialNumberNormalizer

    val language = new LanguageNormalizer

    val blackBerry = new BlackBerryNormalizer
  }

  "YesWapPhoneNormalizer" should {
    "normalize" in new NormalizersScope {
      "user-agent containig (YesWAP mobile phone proxy)" in {
        val normalized = yesWap.normalize("PippoBaudo Mozilla/4.0 (YesWAP mobile phone proxy)")
        normalized must beEqualTo("PippoBaudo")
      }
    }
    "leave unchanged" in new NormalizersScope {
      "Other user-agents" in {
      val src = "PippoBaudo Mozilla/4.0 (YesWOP mobile phone proxy)"
      val expected = src
      val normalized = yesWap.normalize("PippoBaudo Mozilla/4.0 (YesWOP mobile phone proxy)")

      normalized must beEqualTo(expected)
      }
    }
  }

  "BabelFishNormalizer" should {
    "normalize" in new NormalizersScope {
      "user-agents containing (via babelfish.yahoo.com)" in {
        val src = "PippoBaudo (via babelfish.yahoo.com)"
        val expected = "PippoBaudo"
        val normalized = babelFish.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
    "leave unchanged" in new NormalizersScope {
      "Other user-agents" in {
        val src = "PippoBaudo blabla"
        val expected = src
        val normalized = babelFish.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
  }

  "UpLinkNormalizer" should {
    "normalize" in new NormalizersScope {
      "user-agents containing UP.Link" in {
        val src = "PippoBaudo UP.Link bla bla bla"
        val expected = "PippoBaudo"
        val normalized = upLink.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
    "leave unchanged" in new NormalizersScope {
      "Other user-agents" in {
        val src = "PippoBaudo AP.Link xxx xx"
        val expected = src
        val normalized = upLink.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
  }

  "SerialNumberNormalizer" should {
    "normalize" in new NormalizersScope {
      "user-agents containing /SNxxxxxxx" in {
        val src = "PippoBaudo /SN123456 bla bla bla"
        val expected = "PippoBaudo /SNXXXXXX bla bla bla"
        val normalized = serialNumber.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
    "leave unchanged" in new NormalizersScope {
      "Other user-agents" in {
        val src = "PippoBaudo /SW123456 bla bla bla"
        val expected = src
        val normalized = serialNumber.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
  }

  "BlackBerryNormalizer" should {
    "normalize" in new NormalizersScope {
      "user-agents starting with Mozilla" in {
        val src = "Mozilla bla bla bla BlackBerry8800"
        val expected = "BlackBerry8800"
        val normalized = blackBerry.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
    "leave unchanged" in new NormalizersScope {
      "Other user-agents" in {
        val src = "Mozzarella bla bla bla BlackBerry8800"
        val expected = src
        val normalized = blackBerry.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
  }

  "LanguageNormalizzer" should {
    "normalize" in new NormalizersScope {
      "user-agents containing locale" in {
        val src = "Mozilla/5.0 (Linux; U; Android 4.0.4; en-gb; GT-I9300 Build/IMM76D) blabla"
        val expected = "Mozilla/5.0 (Linux; U; Android 4.0.4; xx-xx; GT-I9300 Build/IMM76D) blabla"
        val normalized = language.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
    "leave unchanged" in new NormalizersScope {
      "Other user-agents" in {
        val src = "Mozilla/5.0 (Linux; U; Android 4.0.4; 12-34; GT-I9300 Build/IMM76D) blabla"
        val expected = src
        val normalized = language.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
  }




}
