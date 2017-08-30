package controller.message

import controller.impl.messages.MessageTrade
import controller.impl.messages.imp.{MoveMessage, WrongToPosMessage}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class WrongToPosMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new WrongToPosMessage(new Position(1, 1))
    message.valid should be(false)
  }

  "text" should "be" in {
    val message = new WrongToPosMessage(new Position(1, 1))
    message.text should be("a1 is occupied")
  }


  "equals" should "be if its same class" in {
    val wrongFromPos1: MessageTrade = new WrongToPosMessage(new Position(1, 1))
    val wrongFromPos2: MessageTrade = new WrongToPosMessage(new Position(1, 1))

    wrongFromPos1 should be(wrongFromPos2)
  }

  it should "not be if its not the same text" in {
    val wrongFromPos1: MessageTrade = new WrongToPosMessage(new Position(1, 1))
    val wrongFromPos2: MessageTrade = new WrongToPosMessage(new Position(1, 2))

    wrongFromPos1 should not be wrongFromPos2
  }

  it should "not be if its not same class" in {
    val wrongFromPos1: MessageTrade = new WrongToPosMessage(new Position(1, 1))
    val moveMessage: MessageTrade = new MoveMessage(new Position(1, 1), new Position(1, 2))

    wrongFromPos1 should not be moveMessage
  }

  it should "not be if its not from MessageTrade" in {
    val wrongFromPos1: MessageTrade = new WrongToPosMessage(new Position(1, 2))
    val testString: String = "Test"

    wrongFromPos1 should not be testString
  }
}
