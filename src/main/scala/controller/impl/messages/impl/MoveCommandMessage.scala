package controller.impl.messages.impl

import controller.impl.messages.CommandMessageTrait
import util.Coordinate
import util.position.Position

class MoveCommandMessage(posFrom: Position, posTo: Position) extends CommandMessageTrait {
  override val doText: String = "Move " + Coordinate.moveToCoordinate(posFrom, posTo)
  override val undoText: String = "Undo move " + Coordinate.moveToCoordinate(posTo, posFrom)
}
