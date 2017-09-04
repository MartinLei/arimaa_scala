package controller.impl.command

import controller.impl.messages.MessageTrade
import controller.impl.messages.impl.EmptyUndoStackMessage

class UndoManager {
  var commandStack: List[CommandTrait] = List()

  def doCommand(command: CommandTrait): Any = {
    command.doCommand()

    commandStack = commandStack.::(command)
  }

  def undoCommand(): MessageTrade = {
    if (commandStack.isEmpty)
      return new EmptyUndoStackMessage

    val command: CommandTrait = commandStack.head
    commandStack = commandStack.tail

    command.undoCommand()
  }

}
