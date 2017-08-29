package controller.impl.messages.imp

import controller.impl.messages.MessageTrade

class WrongRabbitMoveMessage extends MessageTrade {
  override val id = 3
  override val valid = false
  override val text = "You can`t move a Rabbit backwards"
}
