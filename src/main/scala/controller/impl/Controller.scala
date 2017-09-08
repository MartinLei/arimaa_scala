package controller.impl

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import controller.impl.command.impl.{MoveCommand, PullCommand}
import controller.impl.command.{ActionCommand, CommandTrait, UndoActionManager}
import controller.impl.messages.impl.Message
import controller.impl.rule.RuleEnum.RuleEnum
import controller.impl.rule.{RuleBook, RuleEnum}
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import model.impl.{Field, PlayerNameEnum}
import util.position.Position

import scala.collection.mutable.ListBuffer

class Controller extends ControllerTrait {
  private val logger = Logger[Controller]
  private val field: FieldTrait = new Field()
  private val ruleBook: RuleBook = new RuleBook(field)

  private var actPlayerName: PlayerNameEnum = PlayerNameEnum.GOLD
  private val undoActionManager = new UndoActionManager

  override def getActPlayerName: PlayerNameEnum = actPlayerName

  override def getFieldAsString: String = {
    field.toString
  }

  override def moveTile(posFrom: Position, posTo: Position): List[String] = {
    val ruleComplaint: RuleEnum = ruleBook.isMoveRuleComplaint(actPlayerName, posFrom, posTo)
    if (!RuleEnum.isValide(ruleComplaint))
      return List(Message.getMessage(ruleComplaint, posFrom, posTo))

    var commandList: ListBuffer[CommandTrait] = ListBuffer()

    ruleComplaint match {
      case RuleEnum.MOVE => commandList.+=(new MoveCommand(field, actPlayerName, posFrom, posTo))
      case RuleEnum.PULL => commandList.+=(new PullCommand(field, PlayerNameEnum.getInvertPlayer(actPlayerName), posFrom, posTo))
    }


    val postRule = ruleBook.postMoveCommand(actPlayerName, posFrom, posTo)
    postRule match {
      case RuleEnum.TRAPPED =>
      //val trapPos = posMessage.pos //TODO
      //commandList.+=(new RemoveCommand(field, actPlayerName, trapPos))
      case RuleEnum.NONE =>
      }


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
