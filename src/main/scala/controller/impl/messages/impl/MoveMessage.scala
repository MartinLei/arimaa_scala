package controller.impl.messages.impl

import controller.impl.messages.MessageTrade
import util.Coordinate
import util.position.Position

class MoveMessage(posFrom: Position, posTo: Position) extends MessageTrade {
  override val valid = true
  override val text: String = "Move " + Coordinate.moveToCoordinate(posFrom, posTo)
}
