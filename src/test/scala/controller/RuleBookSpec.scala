package controller

import controller.impl.RuleBook
import controller.impl.messages.imp.WrongFromPosMessage
import model.impl.Field
import org.scalatest.{FlatSpec, Matchers}
import util.Position

class RuleBookSpec extends FlatSpec with Matchers {

  "precondition" should "check, if posFrom is not None" in {
    val ruleBook = new RuleBook(new Field())

    ruleBook.precondition(new Position(1, 3), new Position(1, 4)) should be(new WrongFromPosMessage)
  }
}
