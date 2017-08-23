package util

class Position(val x: Int, val y: Int) {
  override def toString: String = "{" + x + "," + y + "}"

  override def equals(that: Any): Boolean = that match {
    case that: Position => that.isInstanceOf[Position] && that.hashCode() == this.hashCode()
    case _ => false
  }

  override def hashCode(): Int = {
    toString.hashCode
  }
}
