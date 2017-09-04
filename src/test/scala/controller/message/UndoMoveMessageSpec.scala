package controller.message

import controller.impl.messages.impl.UndoMoveMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class UndoMoveMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new UndoMoveMessage(new Position(1, 1), new Position(2, 1))
    message.valid should be(true)
  }

  "text" should "be" in {
    val message = new UndoMoveMessage(new Position(1, 1), new Position(2, 1))
    message.text should be("Undo move a1e")
  }

}
