package controller.impl.message

import controller.impl.messages.impl.TrappedCommandMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class TrappedCommandMessageSpec extends FlatSpec with Matchers {

  "message" should "be" in {
    val message = new TrappedCommandMessage(new Position(1, 1))
    message.doText should be("Tile gets trapped on a1")
    message.undoText should be("Respawn from dead a1")
  }

}
