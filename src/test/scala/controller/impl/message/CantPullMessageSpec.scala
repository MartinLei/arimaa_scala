package controller.impl.message

import controller.impl.messages.impl.CantPullMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class CantPullMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new CantPullMessage(new Position(1, 1), new Position(2, 1))
    message.valid should be(false)
  }

  "text" should "be" in {
    val message = new CantPullMessage(new Position(1, 1), new Position(2, 1))
    message.text should be("Can’t pull a1e, no own tile surround")
  }

}
