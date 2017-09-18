package controller.impl.command

import controller.impl.messages.MessageText
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
      return List(MessageText.emptyStack)

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

  def isLastCommandAChangePlayer: Boolean = {
    if (actionStack.isEmpty)
      return false

    val lastAction = actionStack.top
    lastAction.isLastAChangePlayerCommand
  }

  def hasPlayerCommand: Boolean = {
    if (actionStack.isEmpty)
      return false

    val lastAction = actionStack.top
    if (lastAction.isLastAChangePlayerCommand)
      return false

    true
  }

  //TODO refactor
  def isLastActionThirdTimeRepetition: Boolean = {
    if (actionStack.isEmpty)
      return false

    if (!hasPlayerCommand)
      return false
    val actionStackSize = actionStack.size - 1
    val actionFirstForward = actionStack.lift(actionStackSize)
    val actionSecondForward = actionStack.lift(actionStackSize - 8)
    val actionThirdForward = actionStack.lift(actionStackSize - 16)

    val actionFirstBackward = actionStack.lift(actionStackSize - 4)
    val actionSecondBackward = actionStack.lift(actionStackSize - 12)

    val forwardCondition = actionFirstForward.equals(actionSecondForward) && actionSecondForward.equals(actionThirdForward)
    val backwardCondition = actionFirstBackward.equals(actionSecondBackward)
    forwardCondition && backwardCondition
  }


}
