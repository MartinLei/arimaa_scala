package controller.impl.command.impl

import controller.impl.command.CommandTrait
import controller.impl.messages.MessageTrade
import controller.impl.messages.impl.UndoRemoveMessage
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.position.Position

class RemoveCommand(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position) extends CommandTrait {
  val tileName: TileNameEnum = field.getTileName(playerName, posFrom)

  override def doCommand(): Unit = {
    field.removeTile(posFrom)
  }

  override def undoCommand(): MessageTrade = {
    field.addTile(playerName, tileName, posFrom)
    new UndoRemoveMessage(posTo, posFrom)
  }
}