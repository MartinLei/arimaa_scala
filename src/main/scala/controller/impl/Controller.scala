package controller.impl

import controller.ControllerTrait
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import model.impl.{Field, PlayerNameEnum}
import util.Position

class Controller extends ControllerTrait {
  private val field: FieldTrait = new Field()

  override def getFieldAsString: String = {
    field.toString
  }

  override def moveTile(posFrom: Position, posTo: Position): Boolean = {
    val actPlayer: PlayerNameEnum = PlayerNameEnum.GOLD // TOOD
    field.moveTile(actPlayer, posFrom, posTo)
  }

  override def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum = {
    field.getTileName(player, pos)
  }
}
