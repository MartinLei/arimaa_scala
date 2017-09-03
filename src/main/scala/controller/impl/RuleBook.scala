package controller.impl

import controller.impl.messages.MessageTrade
import controller.impl.messages.imp._
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.{PlayerNameEnum, TileNameEnum}
import util.DirectionEnum
import util.position.Position

class RuleBook(val field: FieldTrait) {
  def precondition(player: PlayerNameEnum, posFrom: Position, posTo: Position): MessageTrade = {
    val messageIsFromPosNotOwn = isFromPosNotOwn(player, posFrom)
    if (messageIsFromPosNotOwn.isDefined)
      return messageIsFromPosNotOwn.get

    val messageIsToPosNotFree = isToPosNotFree(player, posTo)
    if (messageIsToPosNotFree.isDefined)
      return messageIsToPosNotFree.get

    val messageWrongRabbitMove = isWrongRabbitMove(player, posFrom, posTo)
    if (messageWrongRabbitMove.isDefined)
      return messageWrongRabbitMove.get

    val messageIsFix = isTailFixed(player, posFrom)
    if (messageIsFix.isDefined)
      return messageIsFix.get

    val messageIsTrapped = isTileTrapped(posTo)
    if (messageIsTrapped.isDefined)
      return messageIsTrapped.get

    new MoveMessage(posFrom, posTo)
  }

  def isFromPosNotOwn(actPlayerName: PlayerNameEnum, posFrom: Position): Option[WrongFromPosMessage] = {
    val playerName = field.getPlayerName(posFrom)

    if (!actPlayerName.equals(playerName))
      return Option(new WrongFromPosMessage(posFrom))

    Option(null)
  }

  def isToPosNotFree(actPlayerName: PlayerNameEnum, posTo: Position): Option[WrongToPosMessage] = {
    if (field.isOccupied(posTo))
      return Option(new WrongToPosMessage(posTo))
    Option(null)
  }

  def isWrongRabbitMove(playerName: PlayerNameEnum, posFrom: Position, posTo: Position): Option[WrongRabbitMoveMessage] = {
    if (!field.getTileName(playerName, posFrom).equals(TileNameEnum.RABBIT))
      return Option(null)

    val direction = DirectionEnum.getDirection(posFrom, posTo)
    if (playerName.equals(PlayerNameEnum.GOLD) && direction.equals(DirectionEnum.SOUTH) ||
      playerName.equals(PlayerNameEnum.SILVER) && direction.equals(DirectionEnum.NORTH))
      return Option(new WrongRabbitMoveMessage)

    Option(null)
  }

  def isTailFixed(playerName: PlayerNameEnum, pos: Position): Option[FixTileMessage] = {
    val fixedTilePos = field.getStrongerOtherTilesWhoAround(playerName, pos)
    if (fixedTilePos.isDefined)
      return Option(new FixTileMessage(fixedTilePos.get))

    Option(null)
  }

  def isTileTrapped(pos: Position): Option[TileTrappedMessage] = {
    if (!Position.isPosATrap(pos))
      return Option(null)

    if (field.isHoledByOwnTile(pos))
      return Option(null)

    Option(new TileTrappedMessage(pos))
  }
}
