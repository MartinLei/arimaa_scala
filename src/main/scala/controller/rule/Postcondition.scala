package controller.rule

import controller.impl.messages.impl.TileTrappedMessage
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum
import util.position.Position

object Postcondition {

  def isATileNoTrapped(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position): Option[TileTrappedMessage] = {
    val traps = Position.traps

    traps.foreach(trapPos => {
      if (isATileNoTrapped(field, playerName, posFrom, trapPos))
        return Option(new TileTrappedMessage(trapPos))
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
