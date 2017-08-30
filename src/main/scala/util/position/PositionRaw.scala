package util.position

class PositionRaw(val x: Int, val y: Int) {
  override def equals(that: Any): Boolean = that match {
    case that: PositionRaw => that.isInstanceOf[PositionRaw] && that.hashCode() == this.hashCode()
    case _ => false
  }

  override def hashCode(): Int = toString.hashCode

  override def toString: String = "{" + x + "," + y + "}"
}
