package controller.impl.messages.impl

import controller.impl.messages.CommandMessageTrait
import util.Coordinate
import util.position.Position

class TrappedCommandMessage(pos: Position) extends CommandMessageTrait {
  override val doText: String = "Tile gets trapped on " + Coordinate.posToCoordinate(pos)
  override val undoText: String = "Respawn from dead " + Coordinate.posToCoordinate(pos)
}
