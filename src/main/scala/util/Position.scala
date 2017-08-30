package util


class PositionRAW(val x: Int, val y: Int) {
  override def toString: String = "{" + x + "," + y + "}"

  override def equals(that: Any): Boolean = that match {
    case that: PositionRAW => that.isInstanceOf[PositionRAW] && that.hashCode() == this.hashCode()
    case _ => false
  }

  override def hashCode(): Int = toString.hashCode
}

class Position(x: Int, y: Int) extends PositionRAW(x, y) {
  require(x >= 1 && x <= 8 && y >= 1 && y <= 8, "must be between {1,1} - {8,8}")
}

object Position {
  private val surroundTemplate = Set(new PositionRAW(0, 1), new PositionRAW(1, 0), new PositionRAW(0, -1), new PositionRAW(-1, 0))

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
}
