package controller.impl

import controller.impl.messages.MessageTrade
import controller.impl.messages.imp._
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.{Player, PlayerNameEnum, TileNameEnum}
import util.{DirectionEnum, Position}

class RuleBook(val field: FieldTrait) {
  private var actPlayer: Player = field.getPlayer(PlayerNameEnum.GOLD)
  private var pasPlayer: Player = field.getPlayer(PlayerNameEnum.SILVER)

  def setActPlayer(actPlayerName: PlayerNameEnum): Unit = {
    actPlayer = field.getPlayer(actPlayerName)
    val pasPlayerName = PlayerNameEnum.getInvertPlayer(actPlayerName)
    pasPlayer = field.getPlayer(pasPlayerName)
  }

  def precondition(player: PlayerNameEnum, posFrom: Position, posTo: Position): MessageTrade = {

    if (isFromPosNotOwn(posFrom, posTo))
      return new WrongFromPosMessage

    if (isToPosNotFree(posFrom, posTo))
      return new WrongToPosMessage

    if (isWrongRabbitMove(player, posFrom, posTo))
      return new WrongRabbitMoveMessage


    val messageIsFix = isTailFixed(player, posFrom)
    if (messageIsFix.isDefined)
      return messageIsFix.get

    new MoveMessage(posFrom, posTo)
  }

  private def isFromPosNotOwn(posFrom: Position, posTo: Position): Boolean = {
    field.getTileName(actPlayer.name, posFrom).equals(TileNameEnum.NONE) &&
      !field.getTileName(pasPlayer.name, posFrom).equals(TileNameEnum.NONE)
  }

  private def isToPosNotFree(posFrom: Position, posTo: Position): Boolean = {
    field.isOccupied(posTo)
  }

  private def isWrongRabbitMove(playerName: PlayerNameEnum, posFrom: Position, posTo: Position): Boolean = {
    if (!field.getTileName(playerName, posFrom).equals(TileNameEnum.RABBIT))
      return false

    val direction = DirectionEnum.getDirection(posFrom, posTo)
    if (playerName.equals(PlayerNameEnum.GOLD) && direction.equals(DirectionEnum.SOUTH) ||
      playerName.equals(PlayerNameEnum.SILVER) && direction.equals(DirectionEnum.NORTH))
      return true
    false
  }

  def isTailFixed(playerName: PlayerNameEnum, pos: Position): Option[FixTileMessage] = {
    val fixedTilePos = field.getFixedTilePos(playerName, pos)
    if (fixedTilePos.isDefined)
      return Option(new FixTileMessage(fixedTilePos.get))

    Option(null)
  }
}
