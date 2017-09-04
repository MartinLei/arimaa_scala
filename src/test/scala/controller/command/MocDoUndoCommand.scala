package controller.command

import controller.impl.command.CommandTrait
import controller.impl.messages.impl.UndoMoveMessage
import util.position.Position

class MocDoUndoCommand(testReceiver: MocReceiver) extends CommandTrait {

  override def doCommand(): Unit = {
    testReceiver.add(1)
  }

  override def undoCommand(): UndoMoveMessage = {
    testReceiver.add(-1)
    new UndoMoveMessage(new Position(1, 1), new Position(1, 1))
  }
}
