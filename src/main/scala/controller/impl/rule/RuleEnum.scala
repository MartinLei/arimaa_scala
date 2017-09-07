package controller.impl.rule

object RuleEnum extends Enumeration {
  type RuleEnum = Value
  val NONE: Value = Value("NONE")
  val MOVE: Value = Value("MOVE")
  val PULL: Value = Value("PULL")
  val FROM_POS_NOT_OWN: Value = Value("FROM_POS_NOT_OWN")
  val TO_POS_NOT_FREE: Value = Value("TO_POS_NOT_FREE")
  val WRONG_RABBIT_MOVE: Value = Value("WRONG_RABBIT_MOVE")
  val TILE_FIXED: Value = Value("TILE_FIXED")

  val TRAPPED: Value = Value("TRAPPED")

  def isValide(element: RuleEnum): Boolean = {
    if (element.equals(RuleEnum.MOVE) || element.equals(RuleEnum.PULL))
      return true

    false
  }
}
