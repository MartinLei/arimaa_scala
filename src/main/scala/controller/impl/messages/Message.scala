package controller.impl.messages

import controller.impl.rule.RuleEnum
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

object Message {
  def doMoveMessage(posFrom: Position, posTo: Position): MessageType = {
    MessageType(MessageText.doMove(posFrom, posTo), RuleEnum.MOVE)
  }

  def doPushMessage(posFrom: Position, posTo: Position): MessageType = {
    MessageType(MessageText.doPush(posFrom, posTo), RuleEnum.PUSH)
  }

  def pushNotFinishMessage: MessageType = {
    MessageType(MessageText.pushNotFinish, RuleEnum.PUSH_NOT_FINISH)
  }

  def tileFreezeMessage: MessageType = {
    MessageType(MessageText.freezeTile, RuleEnum.TILE_FREEZE)
  }

  def posFromNotOwnMessage(pos: Position): MessageType = {
    MessageType(MessageText.wrongPosFrom(pos), RuleEnum.POS_FROM_NOT_OWN)
  }

  def posToNotFreeMessage(pos: Position): MessageType = {
    MessageType(MessageText.wrongPosTo(pos), RuleEnum.POS_TO_NOT_FREE)
  }

  def posFromEmptyMessage(pos: Position): MessageType = {
    MessageType(MessageText.posFromEmpty(pos), RuleEnum.POS_FROM_EMPTY)
  }

  def wrongRabbitMoveMessage: MessageType = {
    MessageType(MessageText.wrongRabbitMove, RuleEnum.WRONG_RABBIT_MOVE)
  }

  def doPullMessage(posFrom: Position, posTo: Position): MessageType = {
    MessageType(MessageText.doPull(posFrom, posTo), RuleEnum.PULL)
  }

  def changePlayerMessage(playerName: PlayerNameEnum): MessageType = {
    MessageType(MessageText.changePlayer(playerName), RuleEnum.CHANGE_PLAYER)
  }
}
