package controller.impl.messages.imp

import controller.impl.messages.MessageTrade

class WrongToPosMessage extends MessageTrade {
  override val id = 1
  override val valid = false
  override val text = "Your second coordinate ist wrong"
}
