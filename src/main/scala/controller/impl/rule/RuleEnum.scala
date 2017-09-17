package controller.impl.rule

object RuleEnum extends Enumeration {
  type RuleEnum = Value
  val NONE: Value = Value("NONE")
  val MOVE: Value = Value("MOVE")
  val PUSH: Value = Value("PUSH")
  val POS_FROM_NOT_OWN: Value = Value("POS_FROM_NOT_OWN")
  val POS_TO_NOT_FREE: Value = Value("POS_TO_NOT_FREE")
  val WRONG_RABBIT_MOVE: Value = Value("WRONG_RABBIT_MOVE")
  val TILE_FREEZE: Value = Value("TILE_FREEZE")
  val PUSH_NOT_FINISH: Value = Value("PUSH_NOT_FINISH")
  val PULL: Value = Value("PULL")
  val POS_FROM_EMPTY: Value = Value("POS_FROM_EMPTY")

  val TRAPPED: Value = Value("TRAPPED")
  val CHANGE_PLAYER: Value = Value("CHANGE_PLAYER")

  def isValid(element: RuleEnum): Boolean = {
    if (element.equals(RuleEnum.MOVE) || element.equals(RuleEnum.PUSH) || element.equals(RuleEnum.PULL)
      || element.equals(RuleEnum.CHANGE_PLAYER))
      return true

    false
  }

}
