package controller.impl.rule

import controller.impl.messages.impl.RemoveMessageMessage
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum
import util.position.Position

object Postcondition {

  def isTileTrapped(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position): Option[RemoveMessageMessage] = {
    if (!Position.isPosATrap(posTo))
      return Option(null)

    if (field.isSurroundByOwnTile(playerName, posFrom, posTo))
      return Option(null)

    Option(new RemoveMessageMessage(posTo))
  }

  def isATileNoTrapped(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position): Option[RemoveMessageMessage] = {
    val traps = Position.traps

    traps.foreach(trapPos => {
      if (isATileNoTrapped(field, playerName, posFrom, trapPos))
        return Option(new RemoveMessageMessage(trapPos))
    })

    Option(null)
  }

  private def isATileNoTrapped(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, trapPos: Position): Boolean = {
    if (field.getTileName(playerName, trapPos).equals(TileNameEnum.NONE))
      return false

    if (field.isSurroundByOwnTile(playerName, posFrom, trapPos))
      return false

    true
  }
}
