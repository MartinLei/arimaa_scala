package controller.impl.command

import controller.impl.messages.MessageTrade

class ActionCommand(preCommand: CommandTrait, postCommand: CommandTrait) {

  def doAction(): Unit = {
    preCommand.doCommand()
    postCommand.doCommand()
  }

  def undoAction(): List[MessageTrade] = {
    val preUndoMessage = preCommand.undoCommand()
    val postUndoMessage = postCommand.undoCommand()

    List(preUndoMessage, postUndoMessage)
  }
}
