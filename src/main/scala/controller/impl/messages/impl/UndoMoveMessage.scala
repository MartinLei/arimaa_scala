package controller.impl.messages.impl

import controller.impl.messages.MessageTrade
import util.Coordinate
import util.position.Position

class UndoMoveMessage(posFrom: Position, posTo: Position) extends MessageTrade {
  override val valid = true
  override val text: String = "Undo move " + Coordinate.moveToCoordinate(posFrom, posTo)
}
