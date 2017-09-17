package controller.impl.command.impl

import controller.impl.command.CommandTrait
import controller.impl.messages.MessageText
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

case class MoveCommand(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position) extends CommandTrait {
  override def doCommand(): String = {
    field.changeTilePos(playerName, posFrom, posTo)
    MessageText.doMove(posFrom, posTo)
  }

  override def undoCommand(): String = {
    field.changeTilePos(playerName, posTo, posFrom)
    MessageText.undoMove(posFrom, posTo)
  }
}
