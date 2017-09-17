package controller.impl.rule

import controller.impl.command.ActionManager

object PreChangePlayerCondition {

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
