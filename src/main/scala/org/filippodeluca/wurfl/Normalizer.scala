package org.filippodeluca.wurfl

import java.util.Locale

import scala.collection.JavaConversions._
import scala.util.matching.Regex
import scala.util.matching.Regex.Match

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

object LowerCaseNormalizer extends Normalizer {
  def normalize(src: String): String = src.toLowerCase
}

/**
 * Normalize the User-Agents containing the
 *   "Mozilla/4.0 (YesWAP mobile phone proxy)"
 * statement, removing it
 */
object YesWapMobilePhoneNormalizer extends RegexNormalizer("""\s*Mozilla/4\.0 \(YesWAP mobile phone proxy\).*""".r)

/**
 * Normalize the User-Agent containing the
 *   "(via babelfish.yahoo.com)"
 * statement, removing it
 */
object BabelFishNormalizer extends RegexNormalizer("""\s*\(via babelfish.yahoo.com\)""".r)

/**
 * Normalize the User-Agent containing a serial number pattern:
 *   "/SNnnnn"
 * replacing numbers with sequence of "X" character.
 */
object SerialNumberNormalizer extends RegexNormalizer("""/SN(\d+)\s""".r, m=>"/SN" + "X" * (m.end(1) - m.start(1)) + " ")

/**
 * Normalize the User-Agent ending with the
 *   "UP.Link"
 * statement, removing from it
 */
object UpLinkNormalizer extends Normalizer {
  def normalize(src: String): String = src match {
    case x if(x.contains("UP.Link")) => x.substring(0, x.indexOf("UP.Link")).trim
    case x => x
  }
}


object BlackBerryNormalizer extends Normalizer {
  def normalize(src: String): String = src match {
    case x if (x.contains("BlackBerry") && x.startsWith("Mozilla")) => x.substring(x.indexOf("BlackBerry")).trim
    case x => x
  }
}

object LanguageNormalizer extends RegexNormalizer(Locale.getAvailableLocales.map(x=> """\s""" + x.toString.toLowerCase + ";").mkString("|").r, m=>"xx-xx;")