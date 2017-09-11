package controller.impl.command

import controller.impl.command.impl.{MoveCommand, PushCommand}
import util.position.Position

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

  //TODO ref. give true if equals
  def getPushCommandPosFrom: Option[Position] = {
    if (commandList.isEmpty)
      return Option(null)

    commandList.head match {
      case pushCommand: PushCommand =>
        return Option(pushCommand.posFrom)
      case _ => return Option(null)
    }

    Option(null)
  }

  def isCommandFromPosEqual(pos: Position): Boolean = {
    if (commandList.isEmpty)
      return false

    commandList.head match {
      case pushCommand: PushCommand =>
        return pushCommand.posFrom.equals(pos)
      case moveCommand: MoveCommand =>
        return moveCommand.posFrom.equals(pos)
      case _ => return false
    }

    false
  }
}
