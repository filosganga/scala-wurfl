package org.filippodeluca.wurfl

import org.specs2.mutable.Specification
import org.filippodeluca.wurfl._

/**
 * 
 * @author Filippo De Luca
 */
class NormalizerSpec extends Specification {

  "YesWapPhoneNormalizer" should {
    "normalize" in {
      "user-agent containig (YesWAP mobile phone proxy)" in {
        val normalized = YesWapMobilePhoneNormalizer.normalize("PippoBaudo Mozilla/4.0 (YesWAP mobile phone proxy)")
        normalized must beEqualTo("PippoBaudo")
      }
    }
    "leave unchanged" in {
      "Other user-agents" in {
      val src = "PippoBaudo Mozilla/4.0 (YesWOP mobile phone proxy)"
      val expected = src
      val normalized = YesWapMobilePhoneNormalizer.normalize("PippoBaudo Mozilla/4.0 (YesWOP mobile phone proxy)")

      normalized must beEqualTo(expected)
      }
    }
  }

  "BabelFishNormalizer" should {
    "normalize" in {
      "user-agents containing (via babelfish.yahoo.com)" in {
        val src = "PippoBaudo (via babelfish.yahoo.com)"
        val expected = "PippoBaudo"
        val normalized = BabelFishNormalizer.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
    "leave unchanged" in {
      "Other user-agents" in {
        val src = "PippoBaudo blabla"
        val expected = src
        val normalized = BabelFishNormalizer.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
  }

  "UpLinkNormalizer" should {
    "normalize" in {
      "user-agents containing UP.Link" in {
        val src = "PippoBaudo UP.Link bla bla bla"
        val expected = "PippoBaudo"
        val normalized = UpLinkNormalizer.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
    "leave unchanged" in {
      "Other user-agents" in {
        val src = "PippoBaudo AP.Link xxx xx"
        val expected = src
        val normalized = UpLinkNormalizer.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
  }

  "SerialNumberNormalizer" should {
    "normalize" in {
      "user-agents containing /SNxxxxxxx" in {
        val src = "PippoBaudo /SN123456 bla bla bla"
        val expected = "PippoBaudo /SNXXXXXX bla bla bla"
        val normalized = SerialNumberNormalizer.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
    "leave unchanged" in {
      "Other user-agents" in {
        val src = "PippoBaudo /SW123456 bla bla bla"
        val expected = src
        val normalized = SerialNumberNormalizer.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
  }

  "BlackBerryNormalizer" should {
    "normalize" in {
      "user-agents starting with Mozilla" in {
        val src = "Mozilla bla bla bla BlackBerry8800"
        val expected = "BlackBerry8800"
        val normalized = BlackBerryNormalizer.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
    "leave unchanged" in {
      "Other user-agents" in {
        val src = "Mozzarella bla bla bla BlackBerry8800"
        val expected = src
        val normalized = BlackBerryNormalizer.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
  }

  "LanguageNormalizzer" should {
    "normalize" in {
      "user-agents containing locale" in {
        val src = "Mozilla/5.0 (Linux; U; Android 4.0.4; en-gb; GT-I9300 Build/IMM76D) blabla"
        val expected = "Mozilla/5.0 (Linux; U; Android 4.0.4; xx-xx; GT-I9300 Build/IMM76D) blabla"
        val normalized = LanguageNormalizer.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
    "leave unchanged" in {
      "Other user-agents" in {
        val src = "Mozilla/5.0 (Linux; U; Android 4.0.4; 12-34; GT-I9300 Build/IMM76D) blabla"
        val expected = src
        val normalized = LanguageNormalizer.normalize(src)

        normalized must beEqualTo(expected)
      }
    }
  }



}
