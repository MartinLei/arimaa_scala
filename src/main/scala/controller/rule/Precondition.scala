package controller.rule

import controller.impl.messages.impl._
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.{PlayerNameEnum, TileNameEnum}
import util.DirectionEnum
import util.position.Position

object Precondition {
  def isFromPosNotOwn(field: FieldTrait, actPlayerName: PlayerNameEnum, posFrom: Position): Option[WrongFromPosMessage] = {
    val playerName = field.getPlayerName(posFrom)

    if (!actPlayerName.equals(playerName))
      return Option(new WrongFromPosMessage(posFrom))

    Option(null)
  }

  def isToPosNotFree(field: FieldTrait, actPlayerName: PlayerNameEnum, posTo: Position): Option[WrongToPosMessage] = {
    if (field.isOccupied(posTo))
      return Option(new WrongToPosMessage(posTo))
    Option(null)
  }

  def isWrongRabbitMove(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position): Option[WrongRabbitMoveMessage] = {
    if (!field.getTileName(playerName, posFrom).equals(TileNameEnum.RABBIT))
      return Option(null)

    val direction = DirectionEnum.getDirection(posFrom, posTo)
    if (playerName.equals(PlayerNameEnum.GOLD) && direction.equals(DirectionEnum.SOUTH) ||
      playerName.equals(PlayerNameEnum.SILVER) && direction.equals(DirectionEnum.NORTH))
      return Option(new WrongRabbitMoveMessage)

    Option(null)
  }

  def isTailFixed(field: FieldTrait, playerName: PlayerNameEnum, pos: Position): Option[FixTileMessage] = {
    val fixedTilePos = field.getStrongerOtherTilesWhoAround(playerName, pos)
    if (fixedTilePos.isDefined)
      return Option(new FixTileMessage(fixedTilePos.get))

    Option(null)
  }

  def isTileTrapped(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position): Option[TileTrappedMessage] = {
    if (!Position.isPosATrap(posTo))
      return Option(null)

    if (field.isSurroundByOwnTile(playerName, posFrom, posTo))
      return Option(null)

    Option(new TileTrappedMessage(posTo))
  }
}
