package controller.impl.messages

object MessageEnum extends Enumeration {
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
  val NO_TILE_MOVED: Value = Value("NO_TILE_MOVED")
  val THIRD_TIME_REPETITION: Value = Value("THIRD_TIME_REPETITION")

  def isValid(element: RuleEnum): Boolean = {
    if (element.equals(MessageEnum.MOVE) || element.equals(MessageEnum.PUSH) || element.equals(MessageEnum.PULL)
      || element.equals(MessageEnum.CHANGE_PLAYER))
      return true

    false
  }

}
