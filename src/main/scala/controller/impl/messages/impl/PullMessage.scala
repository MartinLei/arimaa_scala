package controller.impl.messages.impl

import controller.impl.messages.MessageTrade
import util.Coordinate
import util.position.Position

class PullMessage(posFrom: Position, posTo: Position) extends MessageTrade {
  override val valid = true
  override val text: String = "Pull to " + Coordinate.moveToCoordinate(posFrom, posTo)
}
