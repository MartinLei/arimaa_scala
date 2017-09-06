package controller.impl.command

import controller.impl.messages.MessageTrade

class ActionCommand(commandList: List[CommandTrait]) {

  def doAction(): List[MessageTrade] = {
    var doMessageList: List[MessageTrade] = List()

    commandList.reverse.foreach(command => {
      val doMessage = command.doCommand()
      doMessageList = doMessageList.::(doMessage)
    })

    doMessageList
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
