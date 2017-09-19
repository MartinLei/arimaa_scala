package controller.impl.rule

import controller.impl.command.TurnManager

object PreChangePlayerCondition {
  def isMoveThirdTimeRepetition(turnManager: TurnManager): Boolean = {
    // turnManager.isLastActionThirdTimeRepetition
    false
  }


  def isPushNotFinish(turnManager: TurnManager): Boolean = {
    if (!turnManager.isLastAPushCommand)
      return false

    true
  }

  def isNoTileMovedFromPlayer(turnManager: TurnManager): Boolean = {
    /*    if (!turnManager.hasPlayerCommand)
          return true

        turnManager.isLastCommandAChangePlayer
     */ false
  }
}
