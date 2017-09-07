package controller.impl.messages.impl

import controller.impl.messages.CommandMessageTrait
import util.Coordinate
import util.position.Position

class PullCommandMessage(posFrom: Position, posTo: Position) extends CommandMessageTrait {
  override val doText: String = "Pull " + Coordinate.moveToCoordinate(posFrom, posTo)
  override val undoText: String = "Undo pull " + Coordinate.moveToCoordinate(posTo, posFrom)
}
