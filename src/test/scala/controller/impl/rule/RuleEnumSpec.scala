package controller.impl.rule

import org.scalatest.{FlatSpec, Matchers}

class RuleEnumSpec extends FlatSpec with Matchers {

  "isValide" should "return true, if its MOVE or PUSH" in {
    RuleEnum.isValide(RuleEnum.NONE) should be(false)
    RuleEnum.isValide(RuleEnum.MOVE) should be(true)
    RuleEnum.isValide(RuleEnum.PULL) should be(true)
    RuleEnum.isValide(RuleEnum.FROM_POS_NOT_OWN) should be(false)
    RuleEnum.isValide(RuleEnum.TO_POS_NOT_FREE) should be(false)
    RuleEnum.isValide(RuleEnum.WRONG_RABBIT_MOVE) should be(false)
    RuleEnum.isValide(RuleEnum.TILE_FIXED) should be(false)
    RuleEnum.isValide(RuleEnum.TRAPPED) should be(false)
  }
}
