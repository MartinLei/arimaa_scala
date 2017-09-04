package controller.message

import controller.impl.messages.impl.UndoRemoveMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class UndoRemoveMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new UndoRemoveMessage(new Position(1, 1), new Position(2, 1))
    message.valid should be(true)
  }

  "text" should "be" in {
    val message = new UndoRemoveMessage(new Position(1, 1), new Position(2, 1))
    message.text should be("Respawn from dead a1e")
  }

}
