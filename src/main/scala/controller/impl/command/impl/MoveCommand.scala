package controller.impl.command.impl

import controller.impl.command.CommandTrait
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

class MoveCommand(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position) extends CommandTrait {
  override def doCommand(): Unit = {
    field.changeTilePos(playerName, posFrom, posTo)
  }

  override def undoCommand(): Unit = {
    field.changeTilePos(playerName, posTo, posFrom)
  }
}
