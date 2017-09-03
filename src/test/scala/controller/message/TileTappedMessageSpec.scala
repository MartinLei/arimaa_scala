package controller.message

import controller.impl.messages.imp.TileTrappedMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class TileTappedMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new TileTrappedMessage(new Position(1, 1))
    message.valid should be(true)
  }

  "text" should "be" in {
    val message = new TileTrappedMessage(new Position(1, 1))
    message.text should be("Tile gets trapped on a1")
  }

}
