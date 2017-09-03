package controller.command

import controller.impl.command.UndoManager
import org.scalatest.{FlatSpec, Matchers}

class UndoManagerSpec extends FlatSpec with Matchers {
  val testReceiver = new TestReceiver(4)
  val testDoUndoCommand = new TestDoUndoCommand(testReceiver)
  val undoManager = new UndoManager()

  "doCommand" should "do the command" in {
    undoManager.doCommand(testDoUndoCommand)
    testReceiver.sum should be(5)
  }

  "unDoCommand" should "do the command" in {
    undoManager.undoCommand()
    testReceiver.sum should be(4)

    undoManager.undoCommand()
    testReceiver.sum should be(4)
  }

}


