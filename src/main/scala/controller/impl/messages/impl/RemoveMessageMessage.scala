package controller.impl.messages.impl

import controller.impl.messages.MessageTrade
import util.Coordinate
import util.position.Position

class RemoveMessageMessage(val pos: Position) extends MessageTrade {
  override val valid = true
  override val text: String = "Tile gets trapped on " + Coordinate.posToCoordinate(pos)
}
