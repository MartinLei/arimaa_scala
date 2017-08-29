package controller.impl

import controller.impl.messages.MessageTrade
import controller.impl.messages.imp.{MoveMessage, WrongFromPosMessage, WrongRabbitMoveMessage}
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.{Player, PlayerNameEnum, TileNameEnum}
import util.{DirectionEnum, Position}

class RuleBook(val field: FieldTrait) {
  private var playerView: Player = field.getPlayer(PlayerNameEnum.GOLD)

  def setRuleView(playerName: PlayerNameEnum): Unit = {
    playerView = field.getPlayer(playerName)
  }

  def precondition(posFrom: Position, posTo: Position): MessageTrade = {
    if (playerView.getTileName(posFrom).equals(TileNameEnum.NONE))
      return new WrongFromPosMessage

    if (isWrongRabbitMove(posFrom, posTo))
      return new WrongRabbitMoveMessage

    new MoveMessage(posFrom, posTo)
  }

  private def isWrongRabbitMove(posFrom: Position, posTo: Position): Boolean = {
    val direction = DirectionEnum.getDirection(posFrom, posTo)
    if (playerView.name.equals(PlayerNameEnum.GOLD) && direction.equals(DirectionEnum.SOUTH) ||
      playerView.name.equals(PlayerNameEnum.SILVER) && direction.equals(DirectionEnum.NORTH))
      return true
    false
  }
}
