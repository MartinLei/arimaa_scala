package controller.message

import controller.impl.messages.imp.WrongFromPosMessage
import org.scalatest.{FlatSpec, Matchers}

class WrongFromPosSpec extends FlatSpec with Matchers {

  "id" should "be" in {
    val message = new WrongFromPosMessage()
    message.id should be(1)
  }

  "valid" should "be" in {
    val message = new WrongFromPosMessage()
    message.valid should be(false)
  }

  "text" should "be" in {
    val message = new WrongFromPosMessage()
    message.text should be("Your first Coordinate ist wrong")
  }

}
