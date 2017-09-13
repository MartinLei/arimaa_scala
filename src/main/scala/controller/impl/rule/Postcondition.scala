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

  def isATileNowTrapped(field: FieldTrait, posFrom: Position): Option[Position] = {
    val traps = Position.traps

    traps.foreach(trapPos => {
      if (isATileNowTrapped(field, posFrom, trapPos))
        return Option(trapPos)
    })

    Option(null)
  }

  private def isATileNowTrapped(field: FieldTrait, posFrom: Position, trapPos: Position): Boolean = {
    val trapPlayerName = field.getPlayerName(trapPos)

    if (field.getTileName(trapPlayerName, trapPos).equals(TileNameEnum.NONE))
      return false

    if (field.isSurroundByOwnTile(trapPlayerName, posFrom, trapPos))
      return false

    true
  }


}
