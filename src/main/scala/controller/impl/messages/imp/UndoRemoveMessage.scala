package controller.impl.messages.imp

import controller.impl.messages.MessageTrade
import util.Coordinate
import util.position.Position

class UndoRemoveMessage(posFrom: Position, posTo: Position) extends MessageTrade {
  override val valid = true
  override val text: String = "Respawn from dead " + Coordinate.moveToCoordinate(posFrom, posTo)
}
