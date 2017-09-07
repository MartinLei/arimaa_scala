package controller.impl.message

import controller.impl.messages.impl.PullMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PullMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new PullMessage(new Position(1, 1), new Position(2, 1))
    message.valid should be(true)
  }

  "text" should "be" in {
    val message = new PullMessage(new Position(1, 1), new Position(2, 1))
    message.text should be("Pull to a1e")
  }

}
