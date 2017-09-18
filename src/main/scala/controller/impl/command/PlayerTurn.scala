package controller.impl.command

import controller.impl.messages.MessageText
import model.impl.PlayerNameEnum.PlayerNameEnum

import scala.collection.mutable

case class PlayerTurn(name: PlayerNameEnum) {
  private val actionStack: mutable.ArrayStack[ActionCommand] = mutable.ArrayStack()

  def doAction(action: ActionCommand): List[String] = {
    actionStack.push(action)
    action.doAction()
  }

  def undoAction: List[String] = {
    if (actionStack.isEmpty)
      return List(MessageText.emptyStack)

    val lastAction = actionStack.pop()
    lastAction.undoAction()
  }
}
