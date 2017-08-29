package controller.impl.messages.imp

import controller.impl.messages.MessageTrade
import util.{Coordinate, Position}

class MoveMessage(posFrom: Position, posTo: Position) extends MessageTrade {
  override val id = 2
  override val valid = true
  override val text: String = "Move " + Coordinate.moveToCoordinate(posFrom, posTo)
}