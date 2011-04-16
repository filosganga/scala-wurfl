package org.ffdev.swurfl

import io.Source

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 28/12/10
 * Time: 12.25
 * To change this template use File | Settings | File Templates.
 */


object TestUtils {

  def loadRequestDevicesFile: Iterable[UserAgentEntry] = {

    val lines = Source.fromInputStream(getClass.getResourceAsStream("/unit-test.yml")).getLines

    lines.foldLeft(List[UserAgentEntry]()) {
      (list, line) =>
        if (line != null && !line.isEmpty && !line.trim.startsWith("#")) {
          val tokens = line.split("=")
          list :+ new UserAgentEntry(tokens(1), tokens(0))
        }
        else {
          list
        }
    }
  }

}