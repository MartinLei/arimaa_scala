package controller.impl.rule

import controller.impl.command.CommandTrait
import controller.impl.command.impl.RemoveCommand
import controller.impl.rule.RuleEnum.RuleEnum
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

import scala.collection.mutable.ListBuffer

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

  def postMoveCommand(field: FieldTrait, player: PlayerNameEnum, posFrom: Position, posTo: Position): List[CommandTrait] = {
    var commandList: ListBuffer[CommandTrait] = ListBuffer()

    if (Postcondition.isTileTrapped(field, player, posFrom, posTo))
      commandList.+=(new RemoveCommand(field, player, posTo))

    val isNowTrapped: Option[Position] = Postcondition.isATileNowTrapped(field, player, posFrom)
    if (isNowTrapped.isDefined) {
      val trapPos = isNowTrapped.get
      commandList.+=(new RemoveCommand(field, player, trapPos))
    }

    commandList.toList
  }
}
