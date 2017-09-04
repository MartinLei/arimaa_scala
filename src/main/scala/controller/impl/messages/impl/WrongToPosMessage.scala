package controller.impl.messages.impl

import controller.impl.messages.MessageTrade
import util.Coordinate
import util.position.Position

class WrongToPosMessage(pos: Position) extends MessageTrade {
  override val valid = false
  override val text: String = Coordinate.posToCoordinate(pos) + " is occupied"
}
