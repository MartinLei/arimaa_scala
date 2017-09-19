package controller.impl

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import controller.impl.command.impl.{MoveCommand, PullCommand, PushCommand}
import controller.impl.command.{ActionCommand, CommandTrait, TurnManager}
import controller.impl.messages.{MessageEnum, MessageType}
import controller.impl.rule.RuleBook
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import model.impl.{Field, PlayerNameEnum, Tile}
import util.position.Position

import scala.collection.mutable.ListBuffer

class Controller extends ControllerTrait {
  private val logger = Logger[Controller]
  private var field: FieldTrait = new Field()
  private val turnManager = new TurnManager
  turnManager.addTurn(PlayerNameEnum.GOLD)

  def this(playerGoldTiles: Set[Tile], playerSilverTiles: Set[Tile]) {
    this()
    this.field = new Field(playerGoldTiles, playerSilverTiles)
  }

  override def getActPlayerName: PlayerNameEnum = field.actualPlayerName

  override def getFieldAsString: String = {
    field.toString
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

  override def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum = {
    field.getTileName(player, pos)
  }

  override def changePlayer(): List[String] = {
    var commandList: ListBuffer[CommandTrait] = ListBuffer()
    val changePlayerRuleComplaint: MessageType = RuleBook.isChangePlayerRuleComplaint(field, turnManager)
    if (!changePlayerRuleComplaint.isValid)
      return List(changePlayerRuleComplaint.text)

    val nextPlayer = PlayerNameEnum.getInvertPlayer(field.actualPlayerName)
    turnManager.addTurn(nextPlayer)

    val winCommandOption: Option[CommandTrait] = RuleBook.winCommand(field, turnManager)
    if (winCommandOption.isDefined) {
      val winCommand = winCommandOption.get
      commandList.+=(winCommand)
    }

    val action = ActionCommand(commandList.toList)
    turnManager.doAction(action)
  }

}
