package util

object Coordinate {
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
