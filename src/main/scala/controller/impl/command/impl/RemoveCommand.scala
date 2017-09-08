package controller.impl.command.impl

import controller.impl.command.CommandTrait
import controller.impl.messages.Message
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.position.Position

class RemoveCommand(field: FieldTrait, playerName: PlayerNameEnum, pos: Position) extends CommandTrait {
  val tileName: TileNameEnum = field.getTileName(playerName, pos)

  override def doCommand(): String = {
    field.removeTile(pos)
    Message.doTrap(pos)
  }

  override def undoCommand(): String = {
    field.addTile(playerName, tileName, pos)
    Message.undoTrap(pos)
  }
}
