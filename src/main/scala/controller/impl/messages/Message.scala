package controller.impl.messages

import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

object Message {
  def thirdTimeRepetition: MessageType = {
    MessageType(MessageText.thirdTimeRepetition, MessageEnum.THIRD_TIME_REPETITION)
  }

  def doMove(posFrom: Position, posTo: Position): MessageType = {
    MessageType(MessageText.doMove(posFrom, posTo), MessageEnum.MOVE)
  }

  def doPush(posFrom: Position, posTo: Position): MessageType = {
    MessageType(MessageText.doPush(posFrom, posTo), MessageEnum.PUSH)
  }

  def pushNotFinish: MessageType = {
    MessageType(MessageText.pushNotFinish, MessageEnum.PUSH_NOT_FINISH)
  }

  def tileFreeze: MessageType = {
    MessageType(MessageText.freezeTile, MessageEnum.TILE_FREEZE)
  }

  def posFromNotOwn(pos: Position): MessageType = {
    MessageType(MessageText.wrongPosFrom(pos), MessageEnum.POS_FROM_NOT_OWN)
  }

  def posToNotFree(pos: Position): MessageType = {
    MessageType(MessageText.wrongPosTo(pos), MessageEnum.POS_TO_NOT_FREE)
  }

  def posFromEmpty(pos: Position): MessageType = {
    MessageType(MessageText.posFromEmpty(pos), MessageEnum.POS_FROM_EMPTY)
  }

  def wrongRabbitMove: MessageType = {
    MessageType(MessageText.wrongRabbitMove, MessageEnum.WRONG_RABBIT_MOVE)
  }

  def doPull(posFrom: Position, posTo: Position): MessageType = {
    MessageType(MessageText.doPull(posFrom, posTo), MessageEnum.PULL)
  }

  def changePlayer(playerName: PlayerNameEnum): MessageType = {
    MessageType(MessageText.changePlayer(playerName), MessageEnum.CHANGE_PLAYER)
  }

  def noTileMoved: MessageType = {
    MessageType(MessageText.noTileMoved, MessageEnum.NO_TILE_MOVED)
  }

  def endGame: MessageType = {
    MessageType(MessageText.endGame, MessageEnum.END_GAME)
  }
}
