package controller.impl.rule

import org.scalatest.{FlatSpec, Matchers}

class RuleEnumSpec extends FlatSpec with Matchers {

  "isValid" should "return true, if its MOVE or PUSH" in {
    RuleEnum.isValid(RuleEnum.NONE) should be(false)
    RuleEnum.isValid(RuleEnum.MOVE) should be(true)
    RuleEnum.isValid(RuleEnum.PUSH) should be(true)
    RuleEnum.isValid(RuleEnum.FROM_POS_NOT_OWN) should be(false)
    RuleEnum.isValid(RuleEnum.TO_POS_NOT_FREE) should be(false)
    RuleEnum.isValid(RuleEnum.WRONG_RABBIT_MOVE) should be(false)
    RuleEnum.isValid(RuleEnum.TILE_FREEZE) should be(false)
    RuleEnum.isValid(RuleEnum.TRAPPED) should be(false)
  }
}
