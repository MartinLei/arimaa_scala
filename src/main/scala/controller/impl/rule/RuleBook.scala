package controller.impl.rule

import controller.impl.messages.MessageTrade
import controller.impl.messages.impl._
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

class RuleBook(val field: FieldTrait) {
  def isMoveRuleComplaint(playerName: PlayerNameEnum, posFrom: Position, posTo: Position): MessageTrade = {
    val messageIsFromPosNotOwn = Precondition.isFromPosNotOwn(field, playerName, posFrom)
    if (messageIsFromPosNotOwn.isDefined)
      return messageIsFromPosNotOwn.get

    val messageIsToPosNotFree = Precondition.isToPosNotFree(field, playerName, posTo)
    if (messageIsToPosNotFree.isDefined)
      return messageIsToPosNotFree.get

    val messageWrongRabbitMove = Precondition.isWrongRabbitMove(field, playerName, posFrom, posTo)
    if (messageWrongRabbitMove.isDefined)
      return messageWrongRabbitMove.get

    val messageIsFix = Precondition.isTailFixed(field, playerName, posFrom)
    if (messageIsFix.isDefined)
      return messageIsFix.get

    new MoveMessage(posFrom, posTo)
  }

  def postMoveCommand(player: PlayerNameEnum, posFrom: Position, posTo: Position): Option[MessageTrade] = {
    val messageIsTrapped = Postcondition.isTileTrapped(field, player, posFrom, posTo)
    if (messageIsTrapped.isDefined)
      return messageIsTrapped

    val messageTileTrapped = Postcondition.isATileNoTrapped(field, player, posFrom)
    if (messageTileTrapped.isDefined)
      return messageTileTrapped

    Option(null)
  }
}
