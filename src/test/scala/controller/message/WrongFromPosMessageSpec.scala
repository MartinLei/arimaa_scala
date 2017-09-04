package controller.message

import controller.impl.messages.impl.WrongFromPosMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class WrongFromPosMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new WrongFromPosMessage(new Position(1, 1))
    message.valid should be(false)
  }

  "text" should "be" in {
    val message = new WrongFromPosMessage(new Position(1, 1))
    message.text should be("a1 is not your tile")
  }
}
