package controller.impl.command

import controller.impl.messages.MessageText
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

import scala.collection.mutable

class TurnManager {
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

  var playerStack: mutable.ArrayStack[PlayerTurn] = mutable.ArrayStack()

  def addTurn(playerName: PlayerNameEnum): Any = {
    playerStack.push(PlayerTurn(playerName))
  }

  def doAction(action: ActionCommand): List[String] = {
    val player = playerStack.top

    player.doAction(action)
  }

  def undoAction(): List[String] = {
    if (playerStack.isEmpty)
      return List(MessageText.emptyStack)

    var lastPlayer = playerStack.top
    if (lastPlayer.isActionStackEmpty) {
      playerStack.pop()
      if (playerStack.isEmpty)
        return List(MessageText.emptyStack)

      lastPlayer = playerStack.top
    }

    lastPlayer.undoAction
  }


}
