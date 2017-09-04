package controller.impl.command.impl

import controller.impl.command.CommandTrait
import controller.impl.messages.MessageTrade
import controller.impl.messages.impl.UndoMoveMessage
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

class MoveCommand(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position) extends CommandTrait {
  override def doCommand(): Unit = {
    field.changeTilePos(playerName, posFrom, posTo)
  }

  override def undoCommand(): MessageTrade = {
    field.changeTilePos(playerName, posTo, posFrom)
    new UndoMoveMessage(posTo, posFrom)
  }
}
