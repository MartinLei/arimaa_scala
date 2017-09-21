package controller.impl

import controller.ControllerTrait
import controller.impl.ModeEnum.ModeEnum
import controller.impl.command.TurnManager
import controller.impl.messages.MessageEnum
import model.FieldTrait
import model.impl.{Field, PlayerNameEnum}
import util.position.Position

class Controller extends ControllerTrait {
  private var field: FieldTrait = new Field()
  private val turnManager = new TurnManager(PlayerNameEnum.GOLD)
  private var mode: Mode = new GameMode(field, turnManager)

  override def getMode: ModeEnum = {
    if (mode.isInstanceOf[GameMode])
      return ModeEnum.GAME
    if (mode.isInstanceOf[EndMode])
      return ModeEnum.END

    ModeEnum.NONE
  }

  override def changePlayer: String = {
    val changePlayerMessage = mode.changePlayer
    if (changePlayerMessage.messageType.equals(MessageEnum.WIN))
      setMode(ModeEnum.END, field)
    changePlayerMessage.text
  }

  override def getFieldAsString: String = {
    field.toString
  }

  override def moveTile(posFrom: Position, posTo: Position): List[String] = {
    mode.moveTile(posFrom, posTo)
  }

  override def moveTileUndo(): List[String] = {
    mode.moveTileUndo
  }

  override def setMode(modeEnum: ModeEnum, field: FieldTrait): Boolean = modeEnum match {
    case ModeEnum.GAME =>
      this.field = field
      mode = new GameMode(field, turnManager)
      true
    case ModeEnum.END =>
      mode = new EndMode
      true
    case _ => false
  }


}
