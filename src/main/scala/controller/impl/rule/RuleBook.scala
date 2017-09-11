package controller.impl.rule

import controller.impl.command.impl.TrapCommand
import controller.impl.command.{CommandTrait, UndoActionManager}
import controller.impl.rule.RuleEnum.RuleEnum
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

import scala.collection.mutable.ListBuffer

class RuleBook(val field: FieldTrait, undoActionManager: UndoActionManager) {
  def isMoveRuleComplaint(playerName: PlayerNameEnum, posFrom: Position, posTo: Position): RuleEnum = {
    if (Precondition.isPushNotFinish(field, playerName, posTo, undoActionManager))
      return RuleEnum.PUSH_NOT_FINISH

    if (Precondition.isTailPush(field, playerName, posFrom, posTo))
      return RuleEnum.PUSH

    if (Precondition.isTilePull(field, posFrom, posTo, undoActionManager))
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
      commandList.+=(TrapCommand(field, player, posTo))

    val isNowTrapped: Option[Position] = Postcondition.isATileNowTrapped(field, player, posFrom)
    if (isNowTrapped.isDefined) {
      val trapPos = isNowTrapped.get
      commandList.+=(TrapCommand(field, player, trapPos))
    }

    commandList.toList
  }
}
