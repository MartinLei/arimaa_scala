package controller.command

import controller.impl.command.UndoManager
import controller.impl.messages.imp.{EmptyUndoStackMessage, UndoMoveMessage}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class UndoManagerSpec extends FlatSpec with Matchers {
  val mocReciever = new MocReceiver(new Position(5, 5))
  val mocDoUndoCommand = new MocDoUndoCommand(mocReciever)
  val undoManager = new UndoManager()

  "doCommand" should "do the command" in {
    undoManager.doCommand(mocDoUndoCommand)
    mocReciever.pos should be(new Position(6, 6))
  }

  "unDoCommand" should "do the command" in {
    undoManager.undoCommand() should be(new UndoMoveMessage(new Position(1, 1), new Position(1, 1)))
    mocReciever.pos should be(new Position(5, 5))

    undoManager.undoCommand() should be(new EmptyUndoStackMessage)

    mocReciever.pos should be(new Position(5, 5))
  }

}


