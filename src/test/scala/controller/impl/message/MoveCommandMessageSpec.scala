package controller.impl.message

import controller.impl.messages.impl.MoveCommandMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class MoveCommandMessageSpec extends FlatSpec with Matchers {

  "message" should "be" in {
    val message = new MoveCommandMessage(new Position(1, 1), new Position(1, 2))
    message.doText should be("Move a1n")
    message.undoText should be("Undo move a2s")
  }

}
