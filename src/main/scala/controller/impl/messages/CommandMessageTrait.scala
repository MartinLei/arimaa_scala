package controller.impl.messages

trait CommandMessageTrait {
  val doText: String
  val undoText: String

  override def equals(that: Any): Boolean = that match {
    case that: MessageTrade => that.isInstanceOf[MessageTrade] && that.hashCode() == this.hashCode()
    case _ => false
  }

  override def hashCode(): Int = toString.hashCode

  override def toString: String = "{" + this.getClass.getSimpleName + ", text: " + doText + ", undoText: " + undoText + "}"


}
