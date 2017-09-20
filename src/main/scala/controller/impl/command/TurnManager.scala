package controller.impl.command

import controller.impl.messages.MessageText
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

import scala.collection.mutable

class TurnManager {
  var playerStack: mutable.ArrayStack[PlayerTurn] = mutable.ArrayStack()

  def addTurn(playerName: PlayerNameEnum): String = {
    playerStack.push(PlayerTurn(playerName))
    MessageText.changePlayer(playerName)
  }

  def doAction(action: ActionCommand): List[String] = {
    val player = playerStack.top

    player.doAction(action)
  }

  def undoAction(): List[String] = {
    if (playerStack.isEmpty)
      return List(MessageText.emptyStack)

    val lastPlayer = playerStack.top

    if (!lastPlayer.isActionStackEmpty)
      return lastPlayer.undoAction

    playerStack.pop()
    if (playerStack.isEmpty)
      return List(MessageText.emptyStack)

    val newLastPlayer = playerStack.top
    List(MessageText.changePlayer(newLastPlayer.name))
  }

  def isLastActionThirdTimeRepetition: Boolean = {
    if (playerStack.isEmpty)
      return false
    if (playerStack.size < 9)
      return false

    val actionStackSize = playerStack.size - 1
    val actionFirstForward = playerStack.lift(actionStackSize)
    val actionSecondForward = playerStack.lift(actionStackSize - 4)
    val actionThirdForward = playerStack.lift(actionStackSize - 8)

    val actionFirstBackward = playerStack.lift(actionStackSize - 2)
    val actionSecondBackward = playerStack.lift(actionStackSize - 6)

    val forwardCondition = actionFirstForward.equals(actionSecondForward) && actionSecondForward.equals(actionThirdForward)
    val backwardCondition = actionFirstBackward.equals(actionSecondBackward)
    forwardCondition && backwardCondition
  }

  def isTurnEmpty: Boolean = {
    if (playerStack.isEmpty)
      return true

    val lastPlayer = playerStack.top
    lastPlayer.isActionStackEmpty
  }

  def isLastAPushCommand: Boolean = {
    if (playerStack.isEmpty)
      return false

    val lastPlayer = playerStack.top
    lastPlayer.isLastAPushCommand
  }

  def getActualPlayerLastActionPosFrom: Option[Position] = {
    if (playerStack.isEmpty)
      return Option(null)

    val lastPlayer = playerStack.top
    lastPlayer.getLastActionPosFrom
  }

  def getActualPlayerLastActionPosTo: Option[Position] = {
    if (playerStack.isEmpty)
      return Option(null)

    val lastPlayer = playerStack.top
    lastPlayer.getLastActionPosTo
  }


}


