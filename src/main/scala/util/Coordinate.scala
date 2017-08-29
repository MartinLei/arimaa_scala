package util

object Coordinate {
  def moveToCoordinate(posFrom: Position, posTo: Position): String = {
    val sb = new StringBuilder
    sb.append(posToCoordinate(posFrom))
    sb.append(getDirection(posFrom, posTo))
    sb.toString()
  }

  private def getDirection(posFrom: Position, posTo: Position): String = {
    val xd = posTo.x - posFrom.x
    val yd = posTo.y - posFrom.y

    if (xd == 0 && yd == 1)
      return "n"
    else if (xd == 1 && yd == 0)
      return "e"
    else if (xd == 0 && yd == -1)
      return "s"
    else if (xd == -1 && yd == 0)
      return "w"

    "NONE"
  }

  def posToCoordinate(pos: Position): String = {
    val sb = new StringBuilder
    val xChar: Char = (pos.x + 96).toChar
    sb.append(xChar)
    sb.append(pos.y)
    sb.toString()
  }

  def toPosition(coordinates: String): Position = {
    val x: Int = coordinates.charAt(0).toInt - 96
    val y: Int = coordinates.charAt(1).asDigit
    new Position(x, y)
  }
}
