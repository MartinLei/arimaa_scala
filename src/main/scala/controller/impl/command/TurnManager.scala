package controller.impl.command

import controller.impl.messages.MessageText
import model.impl.PlayerNameEnum.PlayerNameEnum

import scala.collection.mutable

class TurnManager {
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
      lastPlayer = playerStack.top
    }
    lastPlayer.undoAction
  }


}
