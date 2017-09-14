package controller.impl.rule

import controller.impl.command.impl.{TrapCommand, WinCommand}
import controller.impl.command.{ActionManager, CommandTrait}
import controller.impl.rule.RuleEnum.RuleEnum
import model.FieldTrait
import model.impl.PlayerNameEnum
import util.position.Position

import scala.collection.mutable.ListBuffer

case class RuleBook() {
  def isMoveRuleComplaint(field: FieldTrait, actionManager: ActionManager, posFrom: Position, posTo: Position): RuleEnum = {
    val playerName = field.actualPlayerName

    if (Precondition.isPosFromEmpty(field, posFrom))
      return RuleEnum.POS_FROM_EMPTY

    if (Precondition.isToPosNotFree(field, posTo))
      return RuleEnum.TO_POS_NOT_FREE

    if (Precondition.isPushNotFinish(field, posTo, actionManager))
      return RuleEnum.PUSH_NOT_FINISH

    if (Precondition.isTailPush(field, playerName, posFrom, posTo))
      return RuleEnum.PUSH

    if (Precondition.isTilePull(field, posFrom, posTo, actionManager))
      return RuleEnum.PULL

    if (Precondition.isPosFromPosNotOwn(field, playerName, posFrom))
      return RuleEnum.POS_FROM_NOT_OWN

    if (Precondition.isWrongRabbitMove(field, playerName, posFrom, posTo))
      return RuleEnum.WRONG_RABBIT_MOVE

    if (Precondition.isTailFreeze(field, playerName, posFrom))
      return RuleEnum.TILE_FREEZE

    RuleEnum.MOVE
  }

  def postMoveCommand(field: FieldTrait, posFrom: Position, posTo: Position): List[CommandTrait] = {
    val playerName = field.actualPlayerName
    var commandList: ListBuffer[CommandTrait] = ListBuffer()

    if (Postcondition.isTileTrapped(field, playerName, posFrom, posTo))
      commandList.+=(TrapCommand(field, playerName, posTo))

    val isNowTrapped: Option[Position] = Postcondition.isATileNowTrapped(field, posFrom)
    if (isNowTrapped.isDefined) {
      val trapPos = isNowTrapped.get
      commandList.+=(TrapCommand(field, playerName, trapPos))
    }

    commandList.toList
  }


  def winCommand(field: FieldTrait): Option[CommandTrait] = {
    val winPlayerNameRabbitOnOtherSide = Postcondition.winByRabbitOnOtherSide(field)
    if (!winPlayerNameRabbitOnOtherSide.equals(PlayerNameEnum.NONE))
      return Option(WinCommand(field, winPlayerNameRabbitOnOtherSide))

    val winPlayerNameKillALlOtherRabbits = Postcondition.winByKillAllOtherRabbits(field)
    if (!winPlayerNameKillALlOtherRabbits.equals(PlayerNameEnum.NONE))
      return Option(WinCommand(field, winPlayerNameKillALlOtherRabbits))

    Option(null)
  }
}
