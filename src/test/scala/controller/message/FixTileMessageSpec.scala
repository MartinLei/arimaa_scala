package controller.message

import controller.impl.messages.imp.FixTileMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class FixTileMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new FixTileMessage(new Position(1, 1))
    message.valid should be(false)
  }

  "text" should "be" in {
    val message = new FixTileMessage(new Position(1, 1))
    message.text should be("You can`t move its fixed by a1")
  }

}
