import aview.tui.Tui
import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import controller.impl.Controller

import scala.io.StdIn.readLine

object Arimaa {
  val logger = Logger("Arimaa")
  val controller: ControllerTrait = new Controller()
  val tui: Tui = new Tui(controller)

  def main(ags: Array[String]): Unit = {
    logger.info("Arimaa")
    logger.info(controller.getFieldAsString)
    logger.info("Input::")

    while (tui.processInputLine(readLine)) {}
  }
}
