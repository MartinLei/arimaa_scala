package controller.impl.messages

trait MessageTrade {
  val id: Int
  val valid: Boolean
  val text: String

  override def equals(that: Any): Boolean = that match {
    case that: MessageTrade => that.isInstanceOf[MessageTrade] && that.hashCode() == this.hashCode()
    case _ => false
  }

  override def hashCode(): Int = id.hashCode

}
