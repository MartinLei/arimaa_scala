package controller.impl.messages.impl

import controller.impl.messages.MessageTrade
import util.Coordinate
import util.position.Position

class UndoRemoveMessage(pos: Position) extends MessageTrade {
  override val valid = true
  override val text: String = "Respawn from dead " + Coordinate.posToCoordinate(pos)
}
