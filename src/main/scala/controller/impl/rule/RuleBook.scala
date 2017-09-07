package controller.impl.rule

import controller.impl.rule.RuleEnum.RuleEnum
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

class RuleBook(val field: FieldTrait) {
  def isMoveRuleComplaint(playerName: PlayerNameEnum, posFrom: Position, posTo: Position): RuleEnum = {

    if (Precondition.isTailPull(field, playerName, posFrom, posTo))
      return RuleEnum.PULL

    if (Precondition.isFromPosNotOwn(field, playerName, posFrom))
      return RuleEnum.FROM_POS_NOT_OWN

    if (Precondition.isToPosNotFree(field, playerName, posTo))
      return RuleEnum.TO_POS_NOT_FREE

    if (Precondition.isWrongRabbitMove(field, playerName, posFrom, posTo))
      return RuleEnum.WRONG_RABBIT_MOVE


    if (Precondition.isTailFixed(field, playerName, posFrom))
      return RuleEnum.TILE_FIXED

    RuleEnum.MOVE
  }

  def postMoveCommand(player: PlayerNameEnum, posFrom: Position, posTo: Position): RuleEnum = {

    if (Postcondition.isTileTrapped(field, player, posFrom, posTo))
      return RuleEnum.TRAPPED

    if (Postcondition.isATileNoTrapped(field, player, posFrom))
      return RuleEnum.TRAPPED

    RuleEnum.NONE
  }
}
