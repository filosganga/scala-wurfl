package org.filippodeluca.swurfl

import io.Source
import org.ffdev.swurfl.repository.{DummyDeviceEnty, DeviceEntry}

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 28/12/10
 * Time: 12.25
 * To change this template use File | Settings | File Templates.
 */


object TestUtils {

  def loadRequestDevicesFile: Iterable[DeviceEntry] = {

    val lines = Source.fromInputStream(getClass.getResourceAsStream("/unit-test.yml")).getLines

    lines.foldLeft(List[DeviceEntry]()) {
      (list, line) =>
        if (line != null && !line.isEmpty && !line.trim.startsWith("#")) {
          val tokens = line.split("=")
          list :+ new DummyDeviceEnty(tokens(1), Some(tokens(0)))
        }
        else {
          list
        }
    }
  }

}