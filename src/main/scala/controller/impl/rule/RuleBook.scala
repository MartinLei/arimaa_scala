package controller.impl.rule

import controller.impl.command.impl.{TrapCommand, WinCommand}
import controller.impl.command.{ActionManager, CommandTrait}
import controller.impl.messages.{MessageText, MessageType}
import model.FieldTrait
import model.impl.PlayerNameEnum
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

import scala.collection.mutable.ListBuffer

object RuleBook {

  def isMoveRuleComplaint(field: FieldTrait, actionManager: ActionManager,
                          playerName: PlayerNameEnum, posFrom: Position, posTo: Position): MessageType = {

    if (PreCondition.isPosFromEmpty(field, posFrom))
      return MessageText.posFromEmptyMessage(posFrom)

    if (PreCondition.isToPosNotFree(field, posTo))
      return MessageText.posToNotFreeMessage(posTo)

    if (PreCondition.isPushNotFinishWithPosTo(field, posTo, actionManager))
      return MessageText.pushNotFinishMessage

    if (PreCondition.isTilePush(field, playerName, posFrom, posTo))
      return MessageText.doPushMessage(posFrom, posTo)

    if (PreCondition.isTilePull(field, posFrom, posTo, actionManager))
      return MessageText.doPullMessage(posFrom, posTo)

    if (PreCondition.isPosFromPosNotOwn(field, playerName, posFrom))
      return MessageText.posFromNotOwnMessage(posFrom)

    if (PreCondition.isWrongRabbitMove(field, playerName, posFrom, posTo))
      return MessageText.wrongRabbitMoveMessage

    if (PreCondition.isTileFreeze(field, playerName, posFrom))
      return MessageText.tileFreezeMessage

    MessageText.doMoveMessage(posFrom, posTo)
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

    //TODO change player not after only a change command is on the stack

    //TODO if action move is 3rd time repetitions

    if (PreChangePlayerCondition.isPushNotFinish(actionManager))
      return MessageText.pushNotFinishMessage

    MessageText.changePlayerMessage(PlayerNameEnum.getInvertPlayer(field.actualPlayerName))
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
