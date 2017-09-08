package controller.impl.rule

import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum
import util.position.Position

object Postcondition {

  def isTileTrapped(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position): Boolean = {
    if (!Position.isPosATrap(posTo))
      return false

    if (field.isSurroundByOwnTile(playerName, posFrom, posTo))
      return false

    true
  }

  def isATileNowTrapped(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position): Option[Position] = {
    val traps = Position.traps

    traps.foreach(trapPos => {
      if (isATileNowTrapped(field, playerName, posFrom, trapPos))
        return Option(trapPos)
    })

    Option(null)
  }

  private def isATileNowTrapped(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, trapPos: Position): Boolean = {
    if (field.getTileName(playerName, trapPos).equals(TileNameEnum.NONE))
      return false

    if (field.isSurroundByOwnTile(playerName, posFrom, trapPos))
      return false

    true
  }


}
