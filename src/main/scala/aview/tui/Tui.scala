package aview.tui

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import controller.impl.messages.MessageTrade
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
    case "c" =>
      controller.changePlayer()
      logger.info("Change Player " + controller.getActPlayerName)
      true
    case "u" =>
      val message = controller.moveTileUndo()
      logger.info(controller.getFieldAsString)
      logger.info(message.text)
      logger.info("Input::")
      true
    case _ =>
      val patternCoordinate = "^[a-h][1-8] [a-h][1-8]$".r
      if ((patternCoordinate findFirstIn input).isDefined) {
        val coordinates = input.split(" ")
        val posFrom: Position = Coordinate.toPosition(coordinates(0))
        val posTo: Position = Coordinate.toPosition(coordinates(1))

        val messageList: List[MessageTrade] = controller.moveTile(posFrom, posTo)
        logger.info(controller.getFieldAsString)
        messageList.foreach(message => logger.info(message.text))
      } else {
        logger.info("Wrong input, use h for help")
      }

      logger.info("Input::")
      true
  }

}
