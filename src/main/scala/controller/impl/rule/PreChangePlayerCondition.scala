package controller.impl.rule

import controller.impl.command.ActionManager
import model.impl.PlayerNameEnum.PlayerNameEnum

object PreChangePlayerCondition {
  def isMoveThirdTimeRepetition(actionManager: ActionManager, playerName: PlayerNameEnum): Boolean = {
    actionManager.isActionThirdTimeRepetition(playerName)
  }


  def isPushNotFinish(actionManager: ActionManager): Boolean = {
    if (!actionManager.isLastAPushCommand)
      return false

    true
  }

  def isNoTileMovedFromPlayer(actionManager: ActionManager): Boolean = {
    if (!actionManager.hasPlayerCommand)
      return true

    actionManager.isLastCommandAChangePlayer
  }
}
