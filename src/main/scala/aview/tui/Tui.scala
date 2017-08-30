package aview.tui

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import util.{Coordinate, Position}

class Tui(controller: ControllerTrait) {
  val logger: Logger = Logger[Tui]

  def processInputLine(input: String): Boolean = input match {
    case "h" =>
      logger.info("Help::")
      logger.info("h : help")
      logger.info("q : quit")
      logger.info("c : change Player")
      logger.info("Input::")
      true
    case "c" =>
      controller.changePlayer()
      logger.info("Change Player " + controller.getActPlayerName)
      true
    case _ =>
      val patternCoordinate = "^[a-h][1-8] [a-h][1-8]$".r
      if ((patternCoordinate findFirstIn input).isDefined) {
        val coordinates = input.split(" ")
        val posFrom: Position = Coordinate.toPosition(coordinates(0))
        val posTo: Position = Coordinate.toPosition(coordinates(1))
        val message = controller.moveTile(posFrom, posTo)
        logger.info(controller.getFieldAsString)
        logger.info(message.text)
      } else {
        logger.info("Wrong input, use h for help")
      }

      logger.info("Input::")
      true
  }

}
