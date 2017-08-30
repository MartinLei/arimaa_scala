package controller.message

import controller.impl.messages.imp.WrongFromPosMessage
import org.scalatest.{FlatSpec, Matchers}

class WrongFromPosMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new WrongFromPosMessage()
    message.valid should be(false)
  }

  "text" should "be" in {
    val message = new WrongFromPosMessage()
    message.text should be("This is not your tile")
  }
}
