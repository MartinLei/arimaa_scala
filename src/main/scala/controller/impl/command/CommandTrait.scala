package controller.impl.command

import controller.impl.messages.MessageTrade

trait CommandTrait {
  def doCommand(): Unit

  def undoCommand(): MessageTrade

}
