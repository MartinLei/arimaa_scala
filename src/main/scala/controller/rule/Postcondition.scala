package controller.rule

import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum
import util.position.Position

object Postcondition {

  def isATileNoTrapped(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position): Boolean = {
    val trapPos = new Position(3, 3)
    val trapSurroundPos = Position.getSurround(trapPos)

    if (field.getTileName(playerName, trapPos).equals(TileNameEnum.NONE))
      return false


    if (field.isSurroundByOwnTile(playerName, posFrom, trapPos))
      return false

    true
  }
}
