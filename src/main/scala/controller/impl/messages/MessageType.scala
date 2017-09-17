package controller.impl.messages

import controller.impl.rule.RuleEnum
import controller.impl.rule.RuleEnum.RuleEnum

case class MessageType(text: String, messageType: RuleEnum) {
  def isValid: Boolean = {
    RuleEnum.isValid(messageType)
  }
}
