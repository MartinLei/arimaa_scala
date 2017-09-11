package controller.impl

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import controller.impl.command.impl.{MoveCommand, PullCommand, PushCommand}
import controller.impl.command.{ActionCommand, CommandTrait, UndoActionManager}
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
  private val undoActionManager = new UndoActionManager

  def this(playerGoldTiles: Set[Tile], playerSilverTiles: Set[Tile]) {
    this()
    this.field = new Field(playerGoldTiles, playerSilverTiles)
  }

  private var actPlayerName: PlayerNameEnum = PlayerNameEnum.GOLD
  override def getActPlayerName: PlayerNameEnum = actPlayerName

  override def getFieldAsString: String = {
    field.toString
  }

  override def moveTile(posFrom: Position, posTo: Position): List[String] = {
    val ruleComplaint: RuleEnum = ruleBook.isMoveRuleComplaint(field, undoActionManager, actPlayerName, posFrom, posTo)
    if (!RuleEnum.isValid(ruleComplaint))
      return List(Message.getMessage(ruleComplaint, posFrom, posTo))

    var commandList: ListBuffer[CommandTrait] = ListBuffer()

    ruleComplaint match {
      case RuleEnum.MOVE => commandList.+=(MoveCommand(field, actPlayerName, posFrom, posTo))
      case RuleEnum.PUSH => commandList.+=(PushCommand(field, PlayerNameEnum.getInvertPlayer(actPlayerName), posFrom, posTo))
      case RuleEnum.PULL => commandList.+=(PullCommand(field, PlayerNameEnum.getInvertPlayer(actPlayerName), posFrom, posTo))
    }

    val postCommandList: List[CommandTrait] = ruleBook.postMoveCommand(field, actPlayerName, posFrom, posTo)
    commandList.++=(postCommandList)

    val action = new ActionCommand(commandList.toList)
    undoActionManager.doAction(action)
  }

  override def moveTileUndo(): List[String] = {
    undoActionManager.undoAction()
  }

  override def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum = {
    field.getTileName(player, pos)
  }

  override def changePlayer(): Unit = {
    actPlayerName = PlayerNameEnum.getInvertPlayer(actPlayerName)
  }

}
