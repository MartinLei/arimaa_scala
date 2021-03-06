package model.impl

object TileNameEnum extends Enumeration {
  type TileNameEnum = Value
  val NONE: Value = Value("NONE")

  val RABBIT: Value = Value("R")
  val CAT: Value = Value("C")
  val DOG: Value = Value("D")
  val HORSE: Value = Value("H")
  val CAMEL: Value = Value("M")
  val ELEPHANT: Value = Value("E")
}
