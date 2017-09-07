package controller.impl.message

import controller.impl.messages.impl.PullCommandMessage
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PullCommandMessageSpec extends FlatSpec with Matchers {

  "message" should "be" in {
    val message = new PullCommandMessage(new Position(1, 1), new Position(1, 2))
    message.doText should be("Pull a1n")
    message.undoText should be("Undo pull a2s")
  }

}
