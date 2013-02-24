/*
 * Copyright 2012.
 *   Filippo De Luca <me@filippodeluca.com>
 *   Fantayeneh Asres Gizaw <fantayeneh@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.scalawurfl

import java.util.Locale
import util.matching.Regex
import util.matching.Regex.Match


/**
 *
 * @author Filippo De Luca
 */
trait Normalizer {

  def normalize(src: String): String

}

class RegexNormalizer(val regex: Regex, val replacer: Match=>String = m=>"") extends Normalizer {

  def normalize(src: String): String = {
    regex.replaceAllIn(src, replacer).trim
  }
}

class LowerCaseNormalizer extends Normalizer {
  def normalize(src: String): String = src.toLowerCase
}

/**
 * Normalize the User-Agents containing the
 *   "Mozilla/4.0 (YesWAP mobile phone proxy)"
 * statement, removing it
 */
class YesWapMobilePhoneNormalizer extends RegexNormalizer("""\s*Mozilla/4\.0 \(YesWAP mobile phone proxy\).*""".r)

/**
 * Normalize the User-Agent containing the
 *   "(via babelfish.yahoo.com)"
 * statement, removing it
 */
class BabelFishNormalizer extends RegexNormalizer("""\s*\(via babelfish.yahoo.com\)""".r)

/**
 * Normalize the User-Agent containing a serial number pattern:
 *   "/SNnnnn"
 * replacing numbers with sequence of "X" character.
 */
class SerialNumberNormalizer extends RegexNormalizer("""/SN(\d+)\s""".r, m=>"/SN" + "X" * (m.end(1) - m.start(1)) + " ")

/**
 * Normalize the User-Agent ending with the
 *   "UP.Link"
 * statement, removing from it
 */
class UpLinkNormalizer extends Normalizer {
  def normalize(src: String): String = src match {
    case x if(x.contains("UP.Link")) => x.substring(0, x.indexOf("UP.Link")).trim
    case x => x
  }
}

class BlackBerryNormalizer extends Normalizer {
  def normalize(src: String): String = src match {
    case x if (x.contains("BlackBerry") && x.startsWith("Mozilla")) => x.substring(x.indexOf("BlackBerry")).trim
    case x => x
  }
}

class LanguageNormalizer extends RegexNormalizer(Locale.getAvailableLocales.map(x=> """\s""" + x.toString.toLowerCase + ";").mkString("|").r, m=>"xx-xx;")