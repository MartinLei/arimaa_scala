package controller.impl.command

import controller.impl.messages.MessageTrade

class ActionCommand(commandList: List[CommandTrait]) {

  def doAction(): Unit = {
    commandList.foreach(command => command.doCommand())
  }

  def undoAction(): List[MessageTrade] = {
    var undoMessageList: List[MessageTrade] = List()

    commandList.reverse.foreach(command => {
      val undoMessage = command.undoCommand()
      undoMessageList = undoMessageList.::(undoMessage)
    })

    undoMessageList
  }
}
