package controller.impl

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import controller.impl.command.impl.{MoveCommand, RemoveCommand}
import controller.impl.command.{ActionCommand, CommandTrait, UndoActionManager}
import controller.impl.messages.MessageTrade
import controller.impl.messages.impl.RemoveMessageMessage
import controller.impl.rule.RuleBook
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
    val ruleComplaintMessage: MessageTrade = ruleBook.isMoveRuleComplaint(actPlayerName, posFrom, posTo)
    if (!ruleComplaintMessage.valid)
      return List("ruleComplaintMessage") //TODO

    var commandList: ListBuffer[CommandTrait] = ListBuffer()
    commandList.+=(new MoveCommand(field, actPlayerName, posFrom, posTo))

    val posMessageOption: Option[MessageTrade] = ruleBook.postMoveCommand(actPlayerName, posFrom, posTo)
    if (posMessageOption.isDefined) {
      val posMessage: MessageTrade = posMessageOption.get
      posMessage match {
        case posMessage: RemoveMessageMessage =>
          val trapPos = posMessage.pos
          commandList.+=(new RemoveCommand(field, actPlayerName, trapPos))
      }
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
