package controller.impl.rule

import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.{PlayerNameEnum, TileNameEnum}
import util.DirectionEnum
import util.position.Position

object Precondition {

  def isFromPosNotOwn(field: FieldTrait, actPlayerName: PlayerNameEnum, posFrom: Position): Boolean = {
    val playerName = field.getPlayerName(posFrom)

    if (!actPlayerName.equals(playerName))
      return true

    false
  }

  def isToPosNotFree(field: FieldTrait, actPlayerName: PlayerNameEnum, posTo: Position): Boolean = {
    if (field.isOccupied(posTo))
      return true
    false
  }

  def isWrongRabbitMove(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position): Boolean = {
    if (!field.getTileName(playerName, posFrom).equals(TileNameEnum.RABBIT))
      return false

    val direction = DirectionEnum.getDirection(posFrom, posTo)
    if (playerName.equals(PlayerNameEnum.GOLD) && direction.equals(DirectionEnum.SOUTH) ||
      playerName.equals(PlayerNameEnum.SILVER) && direction.equals(DirectionEnum.NORTH))
      return true

    false
  }

  def isTailFixed(field: FieldTrait, playerName: PlayerNameEnum, pos: Position): Boolean = {
    val otherPlayerName = PlayerNameEnum.getInvertPlayer(playerName)
    val fixedTilePosList = field.getStrongerTilesWhoAround(otherPlayerName, pos, playerName)

    if (fixedTilePosList.isEmpty)
      return false

    true
  }

  def isTailPull(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position): Boolean = {
    val otherPlayerName = PlayerNameEnum.getInvertPlayer(playerName)
    val otherPlayerTileName = field.getTileName(otherPlayerName, posFrom)

    if (otherPlayerTileName.equals(TileNameEnum.NONE))
      return false

    val strongerSurroundsPasListOption = field.getStrongerTilesWhoAround(playerName, posFrom, otherPlayerName)
    if (strongerSurroundsPasListOption.isEmpty)
      return false

    true
  }
}
