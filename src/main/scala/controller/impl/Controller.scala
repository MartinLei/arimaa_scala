package controller.impl

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import controller.impl.command.impl.{ChangePlayerCommand, MoveCommand, PullCommand, PushCommand}
import controller.impl.command.{ActionCommand, ActionManager, CommandTrait}
import controller.impl.messages.Message
import controller.impl.rule.RuleEnum.RuleEnum
import controller.impl.rule.{RuleBook, RuleEnum}
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import model.impl.{Field, PlayerNameEnum, Tile}
import util.position.Position

import scala.collection.mutable.ListBuffer

class Controller extends ControllerTrait {
  private val ruleBook = RuleBook()
  private val logger = Logger[Controller]
  private var field: FieldTrait = new Field()
  private val actionManager = new ActionManager

  def this(playerGoldTiles: Set[Tile], playerSilverTiles: Set[Tile]) {
    this()
    this.field = new Field(playerGoldTiles, playerSilverTiles)
  }

  override def getActPlayerName: PlayerNameEnum = field.actualPlayerName

  override def getFieldAsString: String = {
    field.toString
  }

  override def moveTile(posFrom: Position, posTo: Position): List[String] = {
    val ruleComplaint: RuleEnum = ruleBook.isMoveRuleComplaint(field, actionManager, posFrom, posTo)
    if (!RuleEnum.isValid(ruleComplaint))
      return List(Message.getMessage(ruleComplaint, posFrom, posTo))

    var commandList: ListBuffer[CommandTrait] = ListBuffer()

    val actPlayerName = field.actualPlayerName
    ruleComplaint match {
      case RuleEnum.MOVE => commandList.+=(MoveCommand(field, actPlayerName, posFrom, posTo))
      case RuleEnum.PUSH => commandList.+=(PushCommand(field, PlayerNameEnum.getInvertPlayer(actPlayerName), posFrom, posTo))
      case RuleEnum.PULL => commandList.+=(PullCommand(field, PlayerNameEnum.getInvertPlayer(actPlayerName), posFrom, posTo))
    }

    val postCommandList: List[CommandTrait] = ruleBook.postMoveCommand(field, posFrom, posTo)
    commandList.++=(postCommandList)

    val action = new ActionCommand(commandList.toList)
    actionManager.doAction(action)
  }

  override def moveTileUndo(): List[String] = {
    actionManager.undoAction()
  }

  override def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum = {
    field.getTileName(player, pos)
  }

  override def changePlayer(): List[String] = {
    var commandList: ListBuffer[CommandTrait] = ListBuffer()
    val changePlayerCommand: CommandTrait = ChangePlayerCommand(field)
    commandList.+=(changePlayerCommand)

    val winCommandOption: Option[CommandTrait] = ruleBook.winCommand(field)
    if (winCommandOption.isDefined) {
      val winCommand = winCommandOption.get
      commandList.+=(winCommand)
    }

    val action = new ActionCommand(commandList.toList)
    actionManager.doAction(action)
  }

}
