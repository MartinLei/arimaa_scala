package controller.impl.message

import controller.impl.messages.impl.CantPullStrongerTileMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class CantPullStrongerTileMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new CantPullStrongerTileMessage(new Position(1, 1), new Position(2, 1))
    message.valid should be(false)
  }

  "text" should "be" in {
    val message = new CantPullStrongerTileMessage(new Position(1, 1), new Position(2, 1))
    message.text should be("Can’t pull a1e, your tile is not strong enough")
  }

}
