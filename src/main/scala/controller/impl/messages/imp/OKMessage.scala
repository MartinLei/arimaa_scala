package controller.impl.messages.imp

import controller.impl.messages.MessageTrade

class OKMessage extends MessageTrade {
  override val valid = true
  override val text = "Valid"
}
