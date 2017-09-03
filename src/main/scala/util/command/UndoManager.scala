package util.command

class UndoManager {
  var commandStack: List[CommandTrait] = List()

  def doCommand(command: CommandTrait): Any = {
    command.doCommand()
    commandStack = commandStack.::(command)
  }

  def undoCommand(): Any = {
    if (commandStack.isEmpty)
      return

    val command: CommandTrait = commandStack.last
    commandStack = commandStack.tail

    command.undoCommand()
  }
}
