package controller.impl.messages.impl

import controller.impl.messages.MessageTrade
import util.Coordinate
import util.position.Position

class CantPullMessage(posFrom: Position, posTo: Position) extends MessageTrade {
  override val valid = false
  override val text: String = "Can’t pull " + Coordinate.moveToCoordinate(posFrom, posTo) + ", no own tile surround"
}
