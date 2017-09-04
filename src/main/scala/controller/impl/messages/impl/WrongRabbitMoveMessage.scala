package controller.impl.messages.impl

import controller.impl.messages.MessageTrade

class WrongRabbitMoveMessage extends MessageTrade {
  override val valid = false
  override val text = "You can`t move a Rabbit backwards"
}
