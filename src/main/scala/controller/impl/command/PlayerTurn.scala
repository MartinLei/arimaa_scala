package controller.impl.command

import controller.impl.messages.MessageText
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

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

  def isLastAPushCommand: Boolean = {
    if (actionStack.isEmpty)
      return false
    val lastAction = actionStack.top

    lastAction.isLastAPushCommand
  }

  def getLastActionPosFrom: Option[Position] = {
    if (actionStack.isEmpty)
      return Option(null)
    val lastAction = actionStack.top

    lastAction.getLastCommandPosFrom
  }

  def getLastActionPosTo: Option[Position] = {
    if (actionStack.isEmpty)
      return Option(null)
    val lastAction = actionStack.top

    lastAction.getLastCommandPosTo
  }

  def isActionStackEmpty: Boolean = {
    actionStack.isEmpty
  }

  override def equals(that: Any): Boolean = that match {
    case that: PlayerTurn => that.isInstanceOf[PlayerTurn] && that.hashCode() == this.hashCode()
    case _ => false
  }

  override def hashCode(): Int = toString.hashCode

  override def toString: String = "{" + name + "," + actionStack + "}"
}
