package util

object Coordinate {

  def toPosition(coordinates: String): Position = {
    val x: Int = coordinates.charAt(0).toInt - 96
    val y: Int = coordinates.charAt(1).asDigit
    new Position(x, y)
  }
}
