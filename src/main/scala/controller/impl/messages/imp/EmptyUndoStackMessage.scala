package controller.impl.messages.imp

import controller.impl.messages.MessageTrade

class EmptyUndoStackMessage extends MessageTrade {
  override val valid = true
  override val text: String = "No move remain to be undo"
}
