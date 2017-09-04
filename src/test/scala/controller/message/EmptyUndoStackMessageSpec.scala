package controller.message

import controller.impl.messages.impl.EmptyUndoStackMessage
import org.scalatest.{FlatSpec, Matchers}


class EmptyUndoStackMessageSpec extends FlatSpec with Matchers {

  "valid" should "be" in {
    val message = new EmptyUndoStackMessage
    message.valid should be(true)
  }

  "text" should "be" in {
    val message = new EmptyUndoStackMessage
    message.text should be("No move remain to be undo")
  }

}
