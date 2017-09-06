package controller.impl.rule

import controller.impl.messages.MessageTrade
import controller.impl.messages.impl._
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

class RuleBook(val field: FieldTrait) {
  def isMoveRuleComplaint(player: PlayerNameEnum, posFrom: Position, posTo: Position): MessageTrade = {
    val messageIsFromPosNotOwn = Precondition.isFromPosNotOwn(field, player, posFrom)
    if (messageIsFromPosNotOwn.isDefined)
      return messageIsFromPosNotOwn.get

    val messageIsToPosNotFree = Precondition.isToPosNotFree(field, player, posTo)
    if (messageIsToPosNotFree.isDefined)
      return messageIsToPosNotFree.get

    val messageWrongRabbitMove = Precondition.isWrongRabbitMove(field, player, posFrom, posTo)
    if (messageWrongRabbitMove.isDefined)
      return messageWrongRabbitMove.get

    val messageIsFix = Precondition.isTailFixed(field, player, posFrom)
    if (messageIsFix.isDefined)
      return messageIsFix.get

    val messageIsTrapped = Precondition.isTileTrapped(field, player, posFrom, posTo)
    if (messageIsTrapped.isDefined)
      return messageIsTrapped.get

    new MoveMessage(posFrom, posTo)
  }

  def postMoveCommand(player: PlayerNameEnum, posFrom: Position, posTo: Position): Option[MessageTrade] = {
    val messageTileTrapped = Postcondition.isATileNoTrapped(field, player, posFrom)
    if (messageTileTrapped.isDefined)
      return messageTileTrapped

    Option(null)
  }
}
