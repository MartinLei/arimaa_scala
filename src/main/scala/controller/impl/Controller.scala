package controller.impl

import controller.ControllerTrait
import controller.impl.ModeEnum.ModeEnum
import controller.impl.command.TurnManager
import controller.impl.messages.MessageText
import model.FieldTrait
import model.impl.{Field, PlayerNameEnum}
import util.position.Position

class Controller extends ControllerTrait {
  private var field: FieldTrait = new Field()
  private val turnManager = new TurnManager(PlayerNameEnum.GOLD)
  private var mode = new GameMode(field, turnManager)

  override def setMode(modeEnum: ModeEnum, field: FieldTrait): Boolean = modeEnum match {
    case ModeEnum.GAME =>
      this.field = field
      mode = new GameMode(field, turnManager)
      true
    case _ => false
  }

  override def getMode: ModeEnum = {
    if (mode.isInstanceOf[GameMode])
      return ModeEnum.GAME

    ModeEnum.NONE
  }

  override def getFieldAsString: String = {
    field.toString
  }

  override def moveTile(posFrom: Position, posTo: Position): List[String] = {
    mode.moveTile(posFrom, posTo)
  }

  override def moveTileUndo(): List[String] = {
    mode.moveTileUndo()
  }

  override def changePlayer(): List[String] = {
    val changePlayerMessage = mode.changePlayer()
    if (!changePlayerMessage.isValid)
      return List(changePlayerMessage.text)

    val winnerName = mode.getWinnerName
    if (!winnerName.equals(PlayerNameEnum.NONE))
      return List(MessageText.doWin(winnerName))

    List(changePlayerMessage.text)
  }


}
