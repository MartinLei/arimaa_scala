package controller.message

import controller.impl.messages.imp.MoveMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class MoveMessageSpec extends FlatSpec with Matchers {

  "id" should "be" in {
    val message = new MoveMessage(new Position(1, 1), new Position(2, 1))
    message.id should be(2)
  }

  "valid" should "be" in {
    val message = new MoveMessage(new Position(1, 1), new Position(2, 1))
    message.valid should be(true)
  }

  "text" should "be" in {
    val message = new MoveMessage(new Position(1, 1), new Position(2, 1))
    message.text should be("Move a1e")
  }

}
