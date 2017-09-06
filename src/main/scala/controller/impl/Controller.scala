package controller.impl

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import controller.impl.command.impl.{MoveCommand, RemoveCommand}
import controller.impl.command.{ActionCommand, CommandTrait, UndoActionManager}
import controller.impl.messages.MessageTrade
import controller.impl.messages.impl.{MoveMessage, TileTrappedMessage}
import controller.impl.rule.RuleBook
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import model.impl.{Field, PlayerNameEnum}
import util.position.Position

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

  override def moveTile(posFrom: Position, posTo: Position): List[MessageTrade] = {
    val ruleComplaintMessage: MessageTrade = ruleBook.isMoveRuleComplaint(actPlayerName, posFrom, posTo)
    if (!ruleComplaintMessage.valid)
      return List(ruleComplaintMessage)

    var commandList: List[CommandTrait] = List()

    ruleComplaintMessage match {
      case preMessage: MoveMessage =>
        commandList = commandList.::(new MoveCommand(field, actPlayerName, posFrom, posTo))
      case preMessage: TileTrappedMessage =>
        commandList = commandList.::(new RemoveCommand(field, actPlayerName, posFrom, posTo))
    }

    val posMessageOption: Option[MessageTrade] = ruleBook.postMoveCommand(actPlayerName, posFrom, posTo)
    if (posMessageOption.isDefined) {
      val posMessage: MessageTrade = posMessageOption.get
      posMessage match {
        case posMessage: TileTrappedMessage =>
          val trapPos = posMessage.pos
          commandList = commandList.::(new RemoveCommand(field, actPlayerName, trapPos, trapPos))
      }
    }

    val action = new ActionCommand(commandList.reverse)
    undoActionManager.doAction(action)
  }

  override def moveTileUndo(): List[MessageTrade] = {
    undoActionManager.undoAction()
  }

  override def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum = {
    field.getTileName(player, pos)
  }

  override def changePlayer(): Unit = {
    actPlayerName = PlayerNameEnum.getInvertPlayer(actPlayerName)
  }

}
