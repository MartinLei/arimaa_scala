package controller.impl.rule

import controller.impl.command.impl.{TrapCommand, WinCommand}
import controller.impl.command.{ActionManager, CommandTrait}
import controller.impl.rule.RuleEnum.RuleEnum
import model.FieldTrait
import model.impl.PlayerNameEnum
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

import scala.collection.mutable.ListBuffer

object RuleBook {
  def isMoveRuleComplaint(field: FieldTrait, actionManager: ActionManager, playerName: PlayerNameEnum, posFrom: Position, posTo: Position): RuleEnum = {
    if (PreCondition.isPosFromEmpty(field, posFrom))
      return RuleEnum.POS_FROM_EMPTY

    if (PreCondition.isToPosNotFree(field, posTo))
      return RuleEnum.TO_POS_NOT_FREE

    if (PreCondition.isPushNotFinish(field, posTo, actionManager))
      return RuleEnum.PUSH_NOT_FINISH

    if (PreCondition.isTilePush(field, playerName, posFrom, posTo))
      return RuleEnum.PUSH

    if (PreCondition.isTilePull(field, posFrom, posTo, actionManager))
      return RuleEnum.PULL

    if (PreCondition.isPosFromPosNotOwn(field, playerName, posFrom))
      return RuleEnum.POS_FROM_NOT_OWN

    if (PreCondition.isWrongRabbitMove(field, playerName, posFrom, posTo))
      return RuleEnum.WRONG_RABBIT_MOVE

    if (PreCondition.isTileFreeze(field, playerName, posFrom))
      return RuleEnum.TILE_FREEZE

    RuleEnum.MOVE
  }

  def postMoveCommand(field: FieldTrait, posFrom: Position, posTo: Position): List[CommandTrait] = {
    val playerName = field.actualPlayerName
    var commandList: ListBuffer[CommandTrait] = ListBuffer()

    if (PostCondition.isTileTrapped(field, playerName, posFrom, posTo))
      commandList.+=(TrapCommand(field, playerName, posTo))

    val isNowTrapped: Option[Position] = PostCondition.isATileNowTrapped(field, posFrom)
    if (isNowTrapped.isDefined) {
      val trapPos = isNowTrapped.get
      commandList.+=(TrapCommand(field, playerName, trapPos))
    }

    commandList.toList
  }

  def winCommand(field: FieldTrait, actionManager: ActionManager): Option[CommandTrait] = {
    val winPlayerNameRabbitOnOtherSide = WinCondition.winByRabbitOnOtherSide(field)
    if (!winPlayerNameRabbitOnOtherSide.equals(PlayerNameEnum.NONE))
      return Option(WinCommand(field, winPlayerNameRabbitOnOtherSide))

    val winPlayerNameKillALlOtherRabbits = WinCondition.winByKillAllOtherRabbits(field)
    if (!winPlayerNameKillALlOtherRabbits.equals(PlayerNameEnum.NONE))
      return Option(WinCommand(field, winPlayerNameKillALlOtherRabbits))

    val winPlayerPassiveCantMove = WinCondition.winByPassivePlayerCantMove(field, actionManager)
    if (!winPlayerPassiveCantMove.equals(PlayerNameEnum.NONE))
      return Option(WinCommand(field, winPlayerPassiveCantMove))

    //TODO  Check if the only moves player B has are 3rd time repetitions. If so player A wins.

    Option(null)
  }
}
