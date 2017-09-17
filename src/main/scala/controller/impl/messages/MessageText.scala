package controller.impl.messages

import model.impl.PlayerNameEnum.PlayerNameEnum
import util.Coordinate
import util.position.Position

object MessageText {
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

  def doPush(posFrom: Position, posTo: Position): String = {
    "Push to " + Coordinate.moveToCoordinate(posFrom, posTo)
  }

  def pushNotFinish: String = {
    "You must first finish your push"
  }

  def freezeTile: String = {
    "You can`t move its freeze"
  }

  def wrongPosFrom(pos: Position): String = {
    Coordinate.posToCoordinate(pos) + " is not your tile"
  }

  def wrongPosTo(pos: Position): String = {
    Coordinate.posToCoordinate(pos) + " is occupied"
  }


  def posFromEmpty(pos: Position): String = {
    Coordinate.posToCoordinate(pos) + " is empty"
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

  def doPull(posFrom: Position, posTo: Position): String = {
    "Pull to " + Coordinate.moveToCoordinate(posFrom, posTo)
  }


  def undoPull(posFrom: Position, posTo: Position): String = {
    "Pull back to " + Coordinate.moveToCoordinate(posFrom, posTo)
  }

  def changePlayer(playerName: PlayerNameEnum): String = {
    playerName + " your turn"
  }

  def doWin(playerName: PlayerNameEnum): String = {
    playerName + " you WIN"
  }

  def undoWin(playerName: PlayerNameEnum): String = {
    playerName + " undo you WIN"
  }
}
