package controller.impl.messages.imp

import controller.impl.messages.MessageTrade
import util.Coordinate
import util.position.Position

class FixTileMessage(pos: Position) extends MessageTrade {
  override val id = 5
  override val valid = false
  override val text: String = "You can`t move its fixed by " + Coordinate.posToCoordinate(pos)
}
