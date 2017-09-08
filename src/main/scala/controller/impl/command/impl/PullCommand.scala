package controller.impl.command.impl

import controller.impl.command.CommandTrait
import controller.impl.messages.Message
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

class PullCommand(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position) extends CommandTrait {
  override def doCommand(): String = {
    field.changeTilePos(playerName, posFrom, posTo)
    Message.doPull(posFrom, posTo)
  }

  override def undoCommand(): String = {
    field.changeTilePos(playerName, posTo, posFrom)
    Message.undoPull(posFrom, posTo)
  }
}
