package controller.impl.command

import controller.impl.messages.MessageTrade

import scala.collection.mutable.ListBuffer

class ActionCommand(commandList: List[CommandTrait]) {

  def doAction(): List[MessageTrade] = {
    var doMessageList: ListBuffer[MessageTrade] = ListBuffer()

    commandList.foreach(command => {
      val doMessage = command.doCommand()
      doMessageList.+=(doMessage)
    })

    doMessageList.toList
  }

  def undoAction(): List[MessageTrade] = {
    var undoMessageList: ListBuffer[MessageTrade] = ListBuffer()

    commandList.reverse.foreach(command => {
      val undoMessage = command.undoCommand()
      undoMessageList.+=(undoMessage)
    })

    undoMessageList.toList
  }
}
