package controller.impl.rule

import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
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

  def isRabbitReachedGoal(field: FieldTrait, posFrom: Position, posTo: Position): PlayerNameEnum = {
    val tilePlayerName = field.getPlayerName(posFrom)
    val tileName = field.getTileName(tilePlayerName, posFrom)

    if (!tileName.equals(TileNameEnum.RABBIT))
      return PlayerNameEnum.NONE

    if (tilePlayerName.equals(PlayerNameEnum.GOLD) && posTo.isPosYEquals(8))
      return PlayerNameEnum.GOLD

    if (tilePlayerName.equals(PlayerNameEnum.SILVER) && posTo.isPosYEquals(1))
      return PlayerNameEnum.SILVER

    PlayerNameEnum.NONE
  }

  def winByKillAllOtherRabbits(field: FieldTrait): PlayerNameEnum = {
    val actPlayerName = field.actualPlayerName
    val pasPlayerName = PlayerNameEnum.getInvertPlayer(actPlayerName)

    if (field.hasNoRabbits(pasPlayerName))
      return actPlayerName
    if (field.hasNoRabbits(actPlayerName))
      return pasPlayerName

    PlayerNameEnum.NONE
  }

  def winByRabbitOnOtherSide(field: Field): PlayerNameEnum = {
    field.hasRabbitOnOtherSide(PlayerNameEnum.GOLD)


    PlayerNameEnum.NONE
  }
}
