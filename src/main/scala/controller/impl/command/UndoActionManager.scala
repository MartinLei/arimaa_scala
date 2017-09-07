package controller.impl.command

import controller.impl.messages.MessageTrade
import controller.impl.messages.impl.EmptyUndoStackMessage

import scala.collection.mutable

class UndoActionManager {
  var actionStack: mutable.ArrayStack[ActionCommand] = mutable.ArrayStack()

  def doAction(action: ActionCommand): List[MessageTrade] = {
    actionStack.push(action)
    action.doAction()
  }

  def undoAction(): List[MessageTrade] = {
    if (actionStack.isEmpty)
      return List(new EmptyUndoStackMessage)

    val lastAction = actionStack.pop()
    lastAction.undoAction()
  }

}
