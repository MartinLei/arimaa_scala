package controller.impl.rule

import controller.impl.command.impl.{TrapCommand, WinCommand}
import controller.impl.command.{ActionManager, CommandTrait}
import controller.impl.messages.{Message, MessageType}
import model.FieldTrait
import model.impl.PlayerNameEnum
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

import scala.collection.mutable.ListBuffer

object RuleBook {

  def isMoveRuleComplaint(field: FieldTrait, actionManager: ActionManager,
                          playerName: PlayerNameEnum, posFrom: Position, posTo: Position): MessageType = {

    if (PreCondition.isPosFromEmpty(field, posFrom))
      return Message.posFromEmpty(posFrom)

    if (PreCondition.isToPosNotFree(field, posTo))
      return Message.posToNotFree(posTo)

    if (PreCondition.isPushNotFinishWithPosTo(field, posTo, actionManager))
      return Message.pushNotFinish

    if (PreCondition.isTilePush(field, playerName, posFrom, posTo))
      return Message.doPush(posFrom, posTo)

    if (PreCondition.isTilePull(field, posFrom, posTo, actionManager))
      return Message.doPull(posFrom, posTo)

    if (PreCondition.isPosFromPosNotOwn(field, playerName, posFrom))
      return Message.posFromNotOwn(posFrom)

    if (PreCondition.isWrongRabbitMove(field, playerName, posFrom, posTo))
      return Message.wrongRabbitMove

    if (PreCondition.isTileFreeze(field, playerName, posFrom))
      return Message.tileFreeze

    Message.doMove(posFrom, posTo)
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

  def isChangePlayerRuleComplaint(field: FieldTrait, actionManager: ActionManager): MessageType = {

    if (PreChangePlayerCondition.isNoTileMovedFromPlayer(actionManager))
      return Message.noTileMoved

    if (PreChangePlayerCondition.isPushNotFinish(actionManager))
      return Message.pushNotFinish

    if (PreChangePlayerCondition.isMoveThirdTimeRepetition(actionManager))
      return Message.thirdTimeRepetition

    Message.changePlayer(PlayerNameEnum.getInvertPlayer(field.actualPlayerName))
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
