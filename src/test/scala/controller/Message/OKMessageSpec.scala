package controller.Message

import controller.impl.messages.imp.OKMessage
import org.scalatest.{FlatSpec, Matchers}

class OKMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new OKMessage()
    message.valid should be(true)
  }

  "text" should "be" in {
    val message = new OKMessage()
    message.text should be("Valid")
  }

}
