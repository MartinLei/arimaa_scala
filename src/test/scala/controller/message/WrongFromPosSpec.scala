package controller.message

import controller.impl.messages.MessageTrade
import controller.impl.messages.imp.{MoveMessage, WrongFromPosMessage}
import org.scalatest.{FlatSpec, Matchers}
import util.Position

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


  "equals" should "be if its same class" in {
    val wrongFromPos1: MessageTrade = new WrongFromPosMessage()
    val wrongFromPos2: MessageTrade = new WrongFromPosMessage()

    wrongFromPos1 should be(wrongFromPos2)
  }

  it should "not be if its not same class" in {
    val wrongFromPos1: MessageTrade = new WrongFromPosMessage()
    val moveMessage: MessageTrade = new MoveMessage(new Position(1, 1), new Position(1, 2))

    wrongFromPos1 should not be moveMessage
  }

  it should "not be if its not from MessageTrade" in {
    val wrongFromPos1: MessageTrade = new WrongFromPosMessage()
    val testString: String = "Test"

    wrongFromPos1 should not be testString
  }
}
