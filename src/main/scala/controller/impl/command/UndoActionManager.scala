package controller.impl.command

import controller.impl.messages.impl.Message

import scala.collection.mutable

class UndoActionManager {
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

}
