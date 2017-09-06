package controller.impl.command.impl

import controller.impl.command.CommandTrait
import controller.impl.messages.MessageTrade
import controller.impl.messages.impl.{TileTrappedMessage, UndoRemoveMessage}
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.position.Position

class RemoveCommand(field: FieldTrait, playerName: PlayerNameEnum, pos: Position) extends CommandTrait {
  val tileName: TileNameEnum = field.getTileName(playerName, pos)

  override def doCommand(): MessageTrade = {
    field.removeTile(pos)
    new TileTrappedMessage(pos)
  }

  override def undoCommand(): MessageTrade = {
    field.addTile(playerName, tileName, pos)
    new UndoRemoveMessage(pos)
  }
}
