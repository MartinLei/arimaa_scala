package controller.impl.rule

import controller.impl.command.impl.TrapCommand
import controller.impl.command.{CommandTrait, UndoActionManager}
import controller.impl.rule.RuleEnum.RuleEnum
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

import scala.collection.mutable.ListBuffer

case class RuleBook() {
  def isMoveRuleComplaint(field: FieldTrait, undoActionManager: UndoActionManager,
                          playerName: PlayerNameEnum, posFrom: Position, posTo: Position): RuleEnum = {

    if (Precondition.isPosFromEmpty(field, posFrom))
      return RuleEnum.POS_FROM_EMPTY

    if (Precondition.isToPosNotFree(field, playerName, posTo))
      return RuleEnum.TO_POS_NOT_FREE

    if (Precondition.isPushNotFinish(field, playerName, posTo, undoActionManager))
      return RuleEnum.PUSH_NOT_FINISH

    if (Precondition.isTailPush(field, playerName, posFrom, posTo))
      return RuleEnum.PUSH

    if (Precondition.isTilePull(field, posFrom, posTo, undoActionManager))
      return RuleEnum.PULL

    if (Precondition.isPosFromPosNotOwn(field, playerName, posFrom))
      return RuleEnum.POS_FROM_NOT_OWN

    if (Precondition.isWrongRabbitMove(field, playerName, posFrom, posTo))
      return RuleEnum.WRONG_RABBIT_MOVE

    if (Precondition.isTailFreeze(field, playerName, posFrom))
      return RuleEnum.TILE_FREEZE

    RuleEnum.MOVE
  }

  def postMoveCommand(field: FieldTrait, player: PlayerNameEnum, posFrom: Position, posTo: Position): List[CommandTrait] = {
    var commandList: ListBuffer[CommandTrait] = ListBuffer()

    if (Postcondition.isTileTrapped(field, player, posFrom, posTo))
      commandList.+=(TrapCommand(field, player, posTo))

    val isNowTrapped: Option[Position] = Postcondition.isATileNowTrapped(field, posFrom)
    if (isNowTrapped.isDefined) {
      val trapPos = isNowTrapped.get
      commandList.+=(TrapCommand(field, player, trapPos))
    }

    commandList.toList
  }
}
