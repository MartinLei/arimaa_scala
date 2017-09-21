package controller.impl.mode

object ModeEnum extends Enumeration {
  type ModeEnum = Value
  val NONE: Value = Value("NONE")
  val GAME: Value = Value("GAME")
  val END: Value = Value("END")
}
