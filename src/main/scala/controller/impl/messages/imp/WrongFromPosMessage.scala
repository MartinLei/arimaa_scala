package controller.impl.messages.imp

import controller.impl.messages.MessageTrade

class WrongFromPosMessage extends MessageTrade {
  override val valid = false
  override val text = "Your first Coordinate ist wrong"
}
