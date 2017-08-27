package util

class Position(val x: Int, val y: Int) {
  require(x >= 1 && x <= 8 && y >= 1 && y <= 8, "must be between {1,1} - {8,8}")


  override def toString: String = "{" + x + "," + y + "}"

  override def equals(that: Any): Boolean = that match {
    case that: Position => that.isInstanceOf[Position] && that.hashCode() == this.hashCode()
    case _ => false
  }

  override def hashCode(): Int = toString.hashCode
}
