package org.filippodeluca.swurfl

import java.io.{InputStreamReader, BufferedReader, FileReader}
import collection.mutable.{ListBuffer, LinkedList}
import io.Source

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 28/12/10
 * Time: 12.25
 * To change this template use File | Settings | File Templates.
 */


class DeviceUserAgent(val id: String, val userAgent: String)

object TestUtils {


  	def loadRequestDevicesFile: Iterable[DeviceUserAgent] = {

      val lines = Source.fromInputStream(getClass.getResourceAsStream("/unit-test.yml")).getLines

      lines.foldLeft(List[DeviceUserAgent]()) {
        (list, line) =>
          if(line != null && !line.isEmpty && !line.trim.startsWith("#")) {
            val tokens = line.split("=")
            list :+ new DeviceUserAgent(tokens(1), tokens(0))
          }
          else {
            list
          }
      }
    }

}