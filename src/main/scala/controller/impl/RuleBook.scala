package controller.impl

import controller.impl.messages.MessageTrade
import controller.impl.messages.imp.{MoveMessage, WrongRabbitMoveMessage, WrongToPosMessage}
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

  def precondition(posFrom: Position, posTo: Position): MessageTrade = {
    if (isToPosNotFree(posFrom, posTo))
      return new WrongToPosMessage

    if (isWrongRabbitMove(posFrom, posTo))
      return new WrongRabbitMoveMessage

    new MoveMessage(posFrom, posTo)
  }

  private def isToPosNotFree(posFrom: Position, posTo: Position): Boolean = {
    field.isOccupied(posTo)
  }

  private def isWrongRabbitMove(posFrom: Position, posTo: Position): Boolean = {
    if (!field.getTileName(actPlayer.name, posFrom).equals(TileNameEnum.RABBIT))
      return false

    val direction = DirectionEnum.getDirection(posFrom, posTo)
    if (actPlayer.name.equals(PlayerNameEnum.GOLD) && direction.equals(DirectionEnum.SOUTH) ||
      actPlayer.name.equals(PlayerNameEnum.SILVER) && direction.equals(DirectionEnum.NORTH))
      return true
    false
  }
}
