package controller.impl.messages

import controller.impl.rule.RuleEnum
import controller.impl.rule.RuleEnum.RuleEnum
import util.Coordinate
import util.position.Position

object Message {
  def doMove(posFrom: Position, posTo: Position): String = {
    "Move " + Coordinate.moveToCoordinate(posFrom, posTo)
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

  def getMessage(rule: RuleEnum, posFrom: Position, posTo: Position): String = {
    rule match {
      case RuleEnum.NONE => "NONE"
      case RuleEnum.MOVE => Message.doMove(posFrom, posTo)
      case RuleEnum.PUSH => Message.doPush(posFrom, posTo)
      case RuleEnum.FROM_POS_NOT_OWN => Message.wrongPosFrom(posFrom)
      case RuleEnum.TO_POS_NOT_FREE => Message.wrongPosTo(posTo)
      case RuleEnum.WRONG_RABBIT_MOVE => Message.wrongRabbitMove
      case RuleEnum.TILE_FIXED => Message.fixTile
      case RuleEnum.PUSH_NOT_FINISH => Message.pushNotFinish

      case RuleEnum.TRAPPED => Message.doTrap(posFrom)
      case _ => "NONE"
    }
  }

  def doPush(posFrom: Position, posTo: Position): String = {
    "Push to " + Coordinate.moveToCoordinate(posFrom, posTo)
  }

  def pushNotFinish: String = {
    "You must finish your push"
  }

  def fixTile: String = {
    "You can`t move its fixed"
  }

  def wrongPosFrom(pos: Position): String = {
    Coordinate.posToCoordinate(pos) + " is not your tile"
  }

  def wrongPosTo(pos: Position): String = {
    Coordinate.posToCoordinate(pos) + " is occupied"
  }


  def emptyStack: String = {
    "No move remain to be undo"
  }

  def wrongRabbitMove: String = {
    "You can`t move a Rabbit backwards"
  }

  def undoPush(posFrom: Position, posTo: Position): String = {
    "Push back to " + Coordinate.moveToCoordinate(posFrom, posTo)
  }

}
