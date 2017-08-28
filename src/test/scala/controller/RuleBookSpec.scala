package controller

import controller.impl.RuleBook
import model.impl.Field
import org.scalatest.{FlatSpec, Matchers}
import util.Position

class RuleBookSpec extends FlatSpec with Matchers {

  /*
  "isMoveAllowed" should "be true, if its players tile" in{
    val ruleBook = new RuleBook(new Field())
    ruleBook.getActPlayerName should be(PlayerNameEnum.GOLD)
    ruleBook.isMoveAllowed(new Position(1,2),new Position(1,3)) should be(true)
    ruleBook.isMoveAllowed(new Position(1,7),new Position(1,6)) should be(true)
  }
  */

  "precondition" should "check, if posFrom is not None" in {
    val ruleBook = new RuleBook(new Field())

    ruleBook.precondition(new Position(1, 3), new Position(1, 4)) should be(false)
  }
}
