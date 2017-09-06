package controller.impl.command

import controller.impl.messages.MessageTrade
import controller.impl.messages.impl.EmptyUndoStackMessage

class UndoActionManager {
  var actionStack: List[ActionCommand] = List()

  def doAction(action: ActionCommand): Any = {
    action.doAction()
    actionStack = actionStack.::(action)
  }

  def undoAction(): List[MessageTrade] = {
    val getLastAction = actionStackPOP()
    if (getLastAction.isDefined) {
      val lastAction: ActionCommand = getLastAction.get
      return lastAction.undoAction()
    }

    List(new EmptyUndoStackMessage)
  }

  private def actionStackPOP(): Option[ActionCommand] = {
    if (actionStack.isEmpty)
      return Option(null)

    val poopedAction: ActionCommand = actionStack.head
    actionStack = actionStack.tail

    Option(poopedAction)
  }

}
