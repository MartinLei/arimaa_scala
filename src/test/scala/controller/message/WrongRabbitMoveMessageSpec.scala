package controller.message

import controller.impl.messages.impl.WrongRabbitMoveMessage
import org.scalatest.{FlatSpec, Matchers}

class WrongRabbitMoveMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new WrongRabbitMoveMessage()
    message.valid should be(false)
  }

  "text" should "be" in {
    val message = new WrongRabbitMoveMessage()
    message.text should be("You can`t move a Rabbit backwards")
  }

}
