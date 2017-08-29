package util

object DirectionEnum extends Enumeration {
  type DirectionEnum = Value
  val NONE: Value = Value("NONE")
  val NORTH: Value = Value("n")
  val EAST: Value = Value("e")
  val SOUTH: Value = Value("s")
  val WEST: Value = Value("w")

  def getDirection(posFrom: Position, posTo: Position): DirectionEnum = {
    val xd = posTo.x - posFrom.x
    val yd = posTo.y - posFrom.y

    if (xd == 0 && yd == 1)
      return DirectionEnum.NORTH
    else if (xd == 1 && yd == 0)
      return DirectionEnum.EAST
    else if (xd == 0 && yd == -1)
      return DirectionEnum.SOUTH
    else if (xd == -1 && yd == 0)
      return DirectionEnum.WEST

    DirectionEnum.NONE
  }
}
