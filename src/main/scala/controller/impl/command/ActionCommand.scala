package controller.impl.command

import controller.impl.command.impl.{ChangePlayerCommand, MoveCommand, PushCommand}
import util.position.Position

import scala.collection.mutable.ListBuffer

case class ActionCommand(commandList: List[CommandTrait]) {

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


  def getLastCommandPosFrom: Option[Position] = {
    if (commandList.isEmpty)
      return Option(null)

    commandList.head match {
      case pushCommand: PushCommand =>
        return Option(pushCommand.posFrom)
      case moveCommand: MoveCommand =>
        return Option(moveCommand.posFrom)
      case _ =>
        return Option(null)
    }

    Option(null)
  }

  def getLastCommandPosTo: Option[Position] = {
    if (commandList.isEmpty)
      return Option(null)

    commandList.head match {
      case pushCommand: PushCommand =>
        return Option(pushCommand.posTo)
      case moveCommand: MoveCommand =>
        return Option(moveCommand.posTo)
      case _ =>
        return Option(null)
    }

    Option(null)
  }

  def isLastAPushCommand: Boolean = {
    if (commandList.isEmpty)
      return false

    val lastCommand = commandList.head
    lastCommand.isInstanceOf[PushCommand]
  }

  def isLastAChangePlayerCommand: Boolean = {
    if (commandList.isEmpty)
      return false

    val lastCommand = commandList.head
    lastCommand.isInstanceOf[ChangePlayerCommand]
  }
}
