package controller.command

import controller.impl.command.UndoManager
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
    undoManager.undoCommand()
    mocReciever.pos should be(new Position(5, 5))

    undoManager.undoCommand()
    mocReciever.pos should be(new Position(5, 5))
  }

}


