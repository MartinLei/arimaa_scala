package controller.impl.message

import controller.impl.messages.impl.RemoveMessageMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class TileTappedMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new RemoveMessageMessage(new Position(1, 1))
    message.valid should be(true)
  }

  "text" should "be" in {
    val message = new RemoveMessageMessage(new Position(1, 1))
    message.text should be("Tile gets trapped on a1")
  }

}
