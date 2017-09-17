package controller.impl.messages


import controller.impl.messages.MessageEnum.RuleEnum

case class MessageType(text: String, messageType: RuleEnum) {
  def isValid: Boolean = {
    MessageEnum.isValid(messageType)
  }
}
