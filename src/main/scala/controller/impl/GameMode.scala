package controller.impl

import controller.impl.command.impl.{MoveCommand, PullCommand, PushCommand}
import controller.impl.command.{ActionCommand, CommandTrait, TurnManager}
import controller.impl.messages.{MessageEnum, MessageType}
import controller.impl.rule.RuleBook
import model.FieldTrait
import model.impl.PlayerNameEnum
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.position.Position

import scala.collection.mutable.ListBuffer

class GameMode(field: FieldTrait, turnManager: TurnManager) extends Mode {
  override def changePlayer(): String = {
    val changePlayerRuleComplaint: MessageType = RuleBook.isChangePlayerRuleComplaint(field, turnManager)
    if (!changePlayerRuleComplaint.isValid)
      return changePlayerRuleComplaint.text

    val nextPlayer = field.changePlayer()
    turnManager.addTurn(nextPlayer)
  }

  def getWinnerName: PlayerNameEnum = {
    RuleBook.getWinner(field, turnManager)
  }

  override def moveTile(posFrom: Position, posTo: Position): List[String] = {
    val actualPlayerName = field.actualPlayerName
    val ruleComplaint: MessageType = RuleBook.isMoveRuleComplaint(field, turnManager, actualPlayerName, posFrom, posTo)
    if (!ruleComplaint.isValid)
      return List(ruleComplaint.text)

    var commandList: ListBuffer[CommandTrait] = ListBuffer()

    val actPlayerName = field.actualPlayerName
    ruleComplaint.messageType match {
      case MessageEnum.MOVE => commandList.+=(MoveCommand(field, actPlayerName, posFrom, posTo))
      case MessageEnum.PUSH => commandList.+=(PushCommand(field, PlayerNameEnum.getInvertPlayer(actPlayerName), posFrom, posTo))
      case MessageEnum.PULL => commandList.+=(PullCommand(field, PlayerNameEnum.getInvertPlayer(actPlayerName), posFrom, posTo))
    }

    val postCommandList: List[CommandTrait] = RuleBook.postMoveCommand(field, posFrom, posTo)
    commandList.++=(postCommandList)

    val action = ActionCommand(commandList.toList)
    turnManager.doAction(action)
  }

  override def moveTileUndo(): List[String] = {
    turnManager.undoAction()
  }

  def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum = {
    field.getTileName(player, pos)
  }

  def getActPlayerName: PlayerNameEnum = field.actualPlayerName
}
