package controller.impl.rule

import controller.impl.command.TurnManager

object PreChangePlayerCondition {
  def isMoveThirdTimeRepetition(turnManager: TurnManager): Boolean = {
    //TODO turnManager.isLastActionThirdTimeRepetition
    false
  }


  def isPushNotFinish(turnManager: TurnManager): Boolean = {
    turnManager.isLastAPushCommand
  }

  def isNoTileMovedFromPlayer(turnManager: TurnManager): Boolean = {
    turnManager.isTurnEmpty
  }
}
