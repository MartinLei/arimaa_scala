package controller.message

import controller.impl.messages.MessageTrade
import controller.impl.messages.imp.{MoveMessage, WrongToPosMessage}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class WrongToPosMessageSpec extends FlatSpec with Matchers {

  "id" should "be" in {
    val message = new WrongToPosMessage()
    message.id should be(1)
  }

  "valid" should "be" in {
    val message = new WrongToPosMessage()
    message.valid should be(false)
  }

  "text" should "be" in {
    val message = new WrongToPosMessage()
    message.text should be("Your second coordinate ist wrong")
  }


  "equals" should "be if its same class" in {
    val wrongFromPos1: MessageTrade = new WrongToPosMessage()
    val wrongFromPos2: MessageTrade = new WrongToPosMessage()

    wrongFromPos1 should be(wrongFromPos2)
  }

  it should "not be if its not same class" in {
    val wrongFromPos1: MessageTrade = new WrongToPosMessage()
    val moveMessage: MessageTrade = new MoveMessage(new Position(1, 1), new Position(1, 2))

    wrongFromPos1 should not be moveMessage
  }

  it should "not be if its not from MessageTrade" in {
    val wrongFromPos1: MessageTrade = new WrongToPosMessage()
    val testString: String = "Test"

    wrongFromPos1 should not be testString
  }
}
