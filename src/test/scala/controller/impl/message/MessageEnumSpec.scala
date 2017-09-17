package controller.impl.message

import controller.impl.messages.MessageEnum
import org.scalatest.{FlatSpec, Matchers}

class MessageEnumSpec extends FlatSpec with Matchers {

  "isValid" should "return true, if its MOVE or PUSH" in {
    MessageEnum.isValid(MessageEnum.NONE) should be(false)
    MessageEnum.isValid(MessageEnum.MOVE) should be(true)
    MessageEnum.isValid(MessageEnum.PUSH) should be(true)
    MessageEnum.isValid(MessageEnum.POS_FROM_NOT_OWN) should be(false)
    MessageEnum.isValid(MessageEnum.POS_TO_NOT_FREE) should be(false)
    MessageEnum.isValid(MessageEnum.WRONG_RABBIT_MOVE) should be(false)
    MessageEnum.isValid(MessageEnum.TILE_FREEZE) should be(false)
    MessageEnum.isValid(MessageEnum.PULL) should be(true)
    MessageEnum.isValid(MessageEnum.POS_FROM_EMPTY) should be(false)

    MessageEnum.isValid(MessageEnum.TRAPPED) should be(false)
  }
}
