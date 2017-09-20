package controller.impl.rule

import controller.impl.command.impl.TrapCommand
import controller.impl.command.{CommandTrait, TurnManager}
import controller.impl.messages.{Message, MessageType}
import model.FieldTrait
import model.impl.PlayerNameEnum
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

import scala.collection.mutable.ListBuffer

object RuleBook {

  def isMoveRuleComplaint(field: FieldTrait, turnManager: TurnManager,
                          playerName: PlayerNameEnum, posFrom: Position, posTo: Position): MessageType = {

    if (PreCondition.isPosFromEmpty(field, posFrom))
      return Message.posFromEmpty(posFrom)

    if (PreCondition.isToPosNotFree(field, posTo))
      return Message.posToNotFree(posTo)

    if (PreCondition.isPushNotFinishWithPosTo(field, posTo, turnManager))
      return Message.pushNotFinish

    if (PreCondition.isTilePush(field, playerName, posFrom, posTo))
      return Message.doPush(posFrom, posTo)

    if (PreCondition.isTilePull(field, posFrom, posTo, turnManager))
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

  def isChangePlayerRuleComplaint(field: FieldTrait, turnManager: TurnManager): MessageType = {

    if (ChangePlayerCondition.isNoTileMovedFromPlayer(turnManager))
      return Message.noTileMoved

    if (ChangePlayerCondition.isPushNotFinish(turnManager))
      return Message.pushNotFinish

    if (ChangePlayerCondition.isMoveThirdTimeRepetition(turnManager))
      return Message.thirdTimeRepetition

    Message.changePlayer(PlayerNameEnum.getInvertPlayer(field.actualPlayerName))
  }

  def getWinner(field: FieldTrait, turnManager: TurnManager): PlayerNameEnum = {
    val winPlayerNameRabbitOnOtherSide = WinCondition.winByRabbitOnOtherSide(field)
    if (!winPlayerNameRabbitOnOtherSide.equals(PlayerNameEnum.NONE))
      return winPlayerNameRabbitOnOtherSide

    val winPlayerNameKillALlOtherRabbits = WinCondition.winByKillAllOtherRabbits(field)
    if (!winPlayerNameKillALlOtherRabbits.equals(PlayerNameEnum.NONE))
      return winPlayerNameKillALlOtherRabbits

    val winPlayerPassiveCantMove = WinCondition.winByPassivePlayerCantMove(field, turnManager)
    if (!winPlayerPassiveCantMove.equals(PlayerNameEnum.NONE))
      return winPlayerPassiveCantMove

    //TODO  Check if the only moves player B has are 3rd time repetitions. If so player A wins.

    PlayerNameEnum.NONE
  }
}
