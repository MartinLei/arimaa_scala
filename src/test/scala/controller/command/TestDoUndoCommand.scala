package controller.command

import controller.impl.command.CommandTrait

class TestDoUndoCommand(testReceiver: TestReceiver) extends CommandTrait {

  override def doCommand(): Unit = {
    testReceiver.add(1)
  }

  override def undoCommand(): Unit = {
    testReceiver.add(-1)
  }
}
