package controller.command

import controller.impl.command.UndoManager
import controller.impl.messages.impl.{EmptyUndoStackMessage, UndoMoveMessage}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class UndoManagerSpec extends FlatSpec with Matchers {
  val mocReceiver = new MocReceiver(new Position(5, 5))
  val mocDoUndoCommand = new MocDoUndoCommand(mocReceiver)
  val undoManager = new UndoManager()

  "doCommand" should "do the command" in {

    undoManager.doCommand(mocDoUndoCommand)
    mocReceiver.pos should be(new Position(6, 6))
    undoManager.doCommand(mocDoUndoCommand)
    mocReceiver.pos should be(new Position(7, 7))
    undoManager.doCommand(mocDoUndoCommand)
    mocReceiver.pos should be(new Position(8, 8))
  }

  "unDoCommand" should "do the command" in {
    undoManager.undoCommand() should be(new UndoMoveMessage(new Position(1, 1), new Position(1, 1)))
    mocReceiver.pos should be(new Position(7, 7))
    undoManager.undoCommand() should be(new UndoMoveMessage(new Position(1, 1), new Position(1, 1)))
    mocReceiver.pos should be(new Position(6, 6))
    undoManager.undoCommand() should be(new UndoMoveMessage(new Position(1, 1), new Position(1, 1)))
    mocReceiver.pos should be(new Position(5, 5))

    undoManager.undoCommand() should be(new EmptyUndoStackMessage)
    mocReceiver.pos should be(new Position(5, 5))
  }

}


