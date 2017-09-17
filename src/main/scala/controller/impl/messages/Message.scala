package controller.impl.messages

import controller.impl.rule.RuleEnum
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.Coordinate
import util.position.Position

object Message {
  def doMove(posFrom: Position, posTo: Position): String = {
    "Move " + Coordinate.moveToCoordinate(posFrom, posTo)
  }

  def doMoveMessage(posFrom: Position, posTo: Position): MessageType = {
    MessageType(doMove(posFrom, posTo), RuleEnum.MOVE)
  }

  def undoMove(posFrom: Position, posTo: Position): String = {
    "Undo move " + Coordinate.moveToCoordinate(posTo, posFrom)
  }

  def doTrap(pos: Position): String = {
    "Gets trapped on " + Coordinate.posToCoordinate(pos)
  }

  def undoTrap(pos: Position): String = {
    "Respawn from dead " + Coordinate.posToCoordinate(pos)
  }

  def doPush(posFrom: Position, posTo: Position): String = {
    "Push to " + Coordinate.moveToCoordinate(posFrom, posTo)
  }

  def doPushMessage(posFrom: Position, posTo: Position): MessageType = {
    MessageType(doPush(posFrom, posTo), RuleEnum.PUSH)
  }

  def pushNotFinish: String = {
    "You must first finish your push"
  }

  def pushNotFinishMessage: MessageType = {
    MessageType(Message.pushNotFinish, RuleEnum.PUSH_NOT_FINISH)
  }

  def freezeTile: String = {
    "You can`t move its freeze"
  }

  def tileFreezeMessage: MessageType = {
    MessageType(freezeTile, RuleEnum.TILE_FREEZE)
  }

  def wrongPosFrom(pos: Position): String = {
    Coordinate.posToCoordinate(pos) + " is not your tile"
  }

  def posFromNotOwnMessage(pos: Position): MessageType = {
    MessageType(wrongPosFrom(pos), RuleEnum.POS_FROM_NOT_OWN)
  }

  def wrongPosTo(pos: Position): String = {
    Coordinate.posToCoordinate(pos) + " is occupied"
  }

  def posToNotFreeMessage(pos: Position): MessageType = {
    MessageType(Message.wrongPosTo(pos), RuleEnum.POS_TO_NOT_FREE)
  }

  def posFromEmpty(pos: Position): String = {
    Coordinate.posToCoordinate(pos) + " is empty"
  }

  def posFromEmptyMessage(pos: Position): MessageType = {
    MessageType(Message.posFromEmpty(pos), RuleEnum.POS_FROM_EMPTY)
  }

  def emptyStack: String = {
    "No move remain to be undo"
  }

  def wrongRabbitMove: String = {
    "You can`t move a Rabbit backwards"
  }

  def wrongRabbitMoveMessage: MessageType = {
    MessageType(wrongRabbitMove, RuleEnum.WRONG_RABBIT_MOVE)
  }

  def undoPush(posFrom: Position, posTo: Position): String = {
    "Push back to " + Coordinate.moveToCoordinate(posFrom, posTo)
  }

  def doPull(posFrom: Position, posTo: Position): String = {
    "Pull to " + Coordinate.moveToCoordinate(posFrom, posTo)
  }

  def doPullMessage(posFrom: Position, posTo: Position): MessageType = {
    MessageType(doPull(posFrom, posTo), RuleEnum.PULL)
  }

  def undoPull(posFrom: Position, posTo: Position): String = {
    "Pull back to " + Coordinate.moveToCoordinate(posFrom, posTo)
  }

  def changePlayer(playerName: PlayerNameEnum): String = {
    playerName + " your turn"
  }

  def changePlayerMessage(playerName: PlayerNameEnum): MessageType = {
    MessageType(changePlayer(playerName), RuleEnum.CHANGE_PLAYER)
  }

  def doWin(playerName: PlayerNameEnum): String = {
    playerName + " you WIN"
  }

  def undoWin(playerName: PlayerNameEnum): String = {
    playerName + " undo you WIN"
  }
}
