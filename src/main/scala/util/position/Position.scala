package util.position


class Position(x: Int, y: Int) extends PositionRaw(x, y) {
  require(x >= 1 && x <= 8 && y >= 1 && y <= 8, "must be between {1,1} - {8,8}")
}

object Position {
  private val surroundTemplate = Set(new PositionRaw(0, 1), new PositionRaw(1, 0), new PositionRaw(0, -1), new PositionRaw(-1, 0))
  val traps: Set[Position] = Set(new Position(3, 3), new Position(6, 3), new Position(3, 6), new Position(6, 6))

  def getSurround(pos: Position): Set[Position] = {
    var surround: Set[Position] = Set()
    surroundTemplate.foreach(f = p => {
      try {
        val newP = new Position(p.x + pos.x, p.y + pos.y)
        surround = surround.+(newP)
      } catch {
        case _: Throwable =>
      }
    })
    surround
  }

  def isPosATrap(pos: Position): Boolean = {
    if (traps.exists((trap: Position) => trap.equals(pos)))
      return true
    false
  }
}
