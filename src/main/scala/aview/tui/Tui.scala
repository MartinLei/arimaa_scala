package aview.tui

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import util.Coordinate
import util.position.Position

class Tui(controller: ControllerTrait) {
  val logger: Logger = Logger[Tui]

  def processInputLine(input: String): Boolean = input match {
    case "h" =>
      logger.info("Help::")
      logger.info("h : help")
      logger.info("q : quit")
      logger.info("c : change Player")
      logger.info("u : undo last move")
      logger.info("Input::")
      true
    case "q" =>
      logger.info("Bye")
      false
    case "c" =>
      val message = controller.changePlayer

      printControllerResponse(List(message))
      true
    case "u" =>
      val messageList: List[String] = controller.moveTileUndo()

      printControllerResponse(messageList)
      true
    case _ =>
      val patternCoordinate = "^[a-h][1-8] [a-h][1-8]$".r
      if ((patternCoordinate findFirstIn input).isDefined) {
        val coordinates = input.split(" ")
        val posFrom: Position = Coordinate.toPosition(coordinates(0))
        val posTo: Position = Coordinate.toPosition(coordinates(1))

        val messageList: List[String] = controller.moveTile(posFrom, posTo)
        printControllerResponse(messageList)
      } else {
        logger.info("Wrong input, use h for help")
        logger.info("Input::")
      }

      true
  }

  private def printControllerResponse(messageList: List[String]): Unit = {
    logger.info(controller.getFieldAsString)
    messageList.foreach(message => logger.info(message))
    logger.info("Input::")
  }
}
