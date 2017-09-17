package controller.impl.command.impl

import controller.impl.command.CommandTrait
import controller.impl.messages.MessageText
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.position.Position

case class TrapCommand(field: FieldTrait, playerName: PlayerNameEnum, pos: Position) extends CommandTrait {
  private var tileName: TileNameEnum = TileNameEnum.NONE

  override def doCommand(): String = {
    tileName = field.getTileName(playerName, pos)

    field.removeTile(pos)
    MessageText.doTrap(pos)
  }

  override def undoCommand(): String = {
    field.addTile(playerName, tileName, pos)
    MessageText.undoTrap(pos)
  }
}
