package controller.impl.messages

import controller.impl.rule.RuleEnum
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

object Message {
  def doMove(posFrom: Position, posTo: Position): MessageType = {
    MessageType(MessageText.doMove(posFrom, posTo), RuleEnum.MOVE)
  }

  def doPush(posFrom: Position, posTo: Position): MessageType = {
    MessageType(MessageText.doPush(posFrom, posTo), RuleEnum.PUSH)
  }

  def pushNotFinish: MessageType = {
    MessageType(MessageText.pushNotFinish, RuleEnum.PUSH_NOT_FINISH)
  }

  def tileFreeze: MessageType = {
    MessageType(MessageText.freezeTile, RuleEnum.TILE_FREEZE)
  }

  def posFromNotOwn(pos: Position): MessageType = {
    MessageType(MessageText.wrongPosFrom(pos), RuleEnum.POS_FROM_NOT_OWN)
  }

  def posToNotFree(pos: Position): MessageType = {
    MessageType(MessageText.wrongPosTo(pos), RuleEnum.POS_TO_NOT_FREE)
  }

  def posFromEmpty(pos: Position): MessageType = {
    MessageType(MessageText.posFromEmpty(pos), RuleEnum.POS_FROM_EMPTY)
  }

  def wrongRabbitMove: MessageType = {
    MessageType(MessageText.wrongRabbitMove, RuleEnum.WRONG_RABBIT_MOVE)
  }

  def doPull(posFrom: Position, posTo: Position): MessageType = {
    MessageType(MessageText.doPull(posFrom, posTo), RuleEnum.PULL)
  }

  def changePlayer(playerName: PlayerNameEnum): MessageType = {
    MessageType(MessageText.changePlayer(playerName), RuleEnum.CHANGE_PLAYER)
  }
}
