package aview.tui

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import util.Position

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
      val patternCoordinate = "^[a-h][1-8] [a-h][1-8]$".r
      if ((patternCoordinate findFirstIn input).isDefined) {
        val coordinates = input.split(" ")
        val posFrom: Position = coordinatesToPosition(coordinates(0))
        val posTo: Position = coordinatesToPosition(coordinates(1))
        val message = controller.moveTile(posFrom, posTo)
        logger.info(controller.getFieldAsString)
        logger.info(message.text)
      } else {
        logger.info("Wrong input, use h for help")
      }

      logger.info("Input::")
      true
  }

  private def coordinatesToPosition(coordinates: String): Position = {
    val x: Int = coordinates.charAt(0).toInt - 96
    val y: Int = coordinates.charAt(1).asDigit
    new Position(x, y)
  }


}
