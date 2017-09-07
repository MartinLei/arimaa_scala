package controller.impl.command

import scala.collection.mutable.ListBuffer

class ActionCommand(commandList: List[CommandTrait]) {

  def doAction(): List[String] = {
    var doMessageList: ListBuffer[String] = ListBuffer()

    commandList.foreach(command => {
      val doMessage = command.doCommand()
      doMessageList.+=(doMessage)
    })

    doMessageList.toList
  }

  def undoAction(): List[String] = {
    var undoMessageList: ListBuffer[String] = ListBuffer()

    commandList.reverse.foreach(command => {
      val undoMessage = command.undoCommand()
      undoMessageList.+=(undoMessage)
    })

    undoMessageList.toList
  }
}
