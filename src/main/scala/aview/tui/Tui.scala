package aview.tui

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait

class Tui(controller: ControllerTrait) {
  val logger: Logger = Logger[Tui]

  def processInputLine(input: String): Boolean = input match {
    case "h" =>
      logger.info("Help::")
      logger.info("h : help")
      logger.info("q : quit")
      logger.info("Input::")
      true
    case "q" =>
      logger.info("Bye")
      false
    case _ =>
      logger.info(controller.getFieldAsString)
      logger.info("Input::")
      true
  }

}
