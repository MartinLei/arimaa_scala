package controller.impl.messages.impl

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

  def emptyStack: String = {
    "No move remain to be undo"
  }

}
