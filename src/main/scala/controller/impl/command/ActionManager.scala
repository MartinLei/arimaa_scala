package controller.impl.command

import controller.impl.messages.Message
import util.position.Position

import scala.collection.mutable

class ActionManager {

  var actionStack: mutable.ArrayStack[ActionCommand] = mutable.ArrayStack()

  def doAction(action: ActionCommand): List[String] = {
    actionStack.push(action)
    action.doAction()
  }

  def undoAction(): List[String] = {
    if (actionStack.isEmpty)
      return List(Message.emptyStack)

    val lastAction = actionStack.pop()
    lastAction.undoAction()
  }

  def getLastActionPushCommandPosFrom: Option[Position] = {
    if (actionStack.isEmpty)
      return Option(null)

    val lastAction = actionStack.top
    lastAction.getPushCommandPosFrom
  }

  def getLastActionCommandPosFrom: Option[Position] = {
    if (actionStack.isEmpty)
      return Option(null)

    val lastAction = actionStack.top
    lastAction.getLastCommandPosFrom
  }

  def getLastActionCommandPosTo: Option[Position] = {
    if (actionStack.isEmpty)
      return Option(null)

    val lastAction = actionStack.top
    lastAction.getLastCommandPosTo
  }

  def isLastAPushCommand: Boolean = {
    if (actionStack.isEmpty)
      return false

    val lastAction = actionStack.top
    lastAction.isLastAPushCommand
  }
}
