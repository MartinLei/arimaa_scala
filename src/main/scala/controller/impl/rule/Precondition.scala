package controller.impl.rule

import controller.impl.command.UndoActionManager
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

  def isTailFreeze(field: FieldTrait, playerName: PlayerNameEnum, pos: Position): Boolean = {
    val otherPlayerName = PlayerNameEnum.getInvertPlayer(playerName)
    val freezeTilePosList = field.getStrongerTilesWhoAround(otherPlayerName, pos, playerName)

    if (freezeTilePosList.isEmpty)
      return false

    true
  }

  def isTailPush(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position): Boolean = {
    val otherPlayerName = PlayerNameEnum.getInvertPlayer(playerName)
    val otherPlayerTileName = field.getTileName(otherPlayerName, posFrom)

    if (field.isOccupied(posTo))
      return false

    if (otherPlayerTileName.equals(TileNameEnum.NONE))
      return false

    val strongerSurroundsPasListOption = field.getStrongerTilesWhoAround(playerName, posFrom, otherPlayerName)
    if (strongerSurroundsPasListOption.isEmpty)
      return false

    true
  }

  def isPushNotFinish(field: FieldTrait, playerName: PlayerNameEnum, posTo: Position, undoActionManager: UndoActionManager): Boolean = {
    val posFromPushCommandOption = undoActionManager.getLastActionPushCommandPosFrom
    if (posFromPushCommandOption.isDefined) {
      val posFromPushCommand: Position = posFromPushCommandOption.get
      if (!posFromPushCommand.equals(posTo))
        return true
    }

    false
  }

  def isTilePull(field: FieldTrait, posFrom: Position, posTo: Position, undoActionManager: UndoActionManager): Boolean = {
    val lastPosFromOption = undoActionManager.getLastActionCommandPosFrom
    val lastPosToOption = undoActionManager.getLastActionCommandPosTo

    if (lastPosFromOption.isDefined && lastPosToOption.isDefined) {
      val lastPosFrom = lastPosFromOption.get
      val lastPosTo = lastPosToOption.get
      val lastTilePlayerName = field.getPlayerName(lastPosTo)
      val actTilePlayerName = field.getPlayerName(posFrom)
      if (lastTilePlayerName.equals(actTilePlayerName))
        return false

      if (!lastPosFrom.equals(posTo))
        return false

      val lastPosTileName = field.getTileName(lastTilePlayerName, lastPosTo)
      val actPosTileName = field.getTileName(actTilePlayerName, posFrom)
      if (lastPosTileName.compare(actPosTileName) > 0)
        return true
    }
    false
  }
}
