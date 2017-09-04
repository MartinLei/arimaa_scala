package controller.impl.messages

trait MessageTrade {
  val valid: Boolean
  val text: String

  override def equals(that: Any): Boolean = that match {
    case that: MessageTrade => that.isInstanceOf[MessageTrade] && that.hashCode() == this.hashCode()
    case _ => false
  }

  override def toString: String = "{" + this.getClass.getSimpleName + ", text: " + text + ", valid: " + valid + "}"

  override def hashCode(): Int = toString.hashCode

}
