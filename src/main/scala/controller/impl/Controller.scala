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
    val preMessage: MessageTrade = ruleBook.isMoveRuleComplaint(actPlayerName, posFrom, posTo)
    if (!preMessage.valid)
      return List(preMessage)

    var commandList: List[CommandTrait] = List()

    preMessage match {
      case preMessage: MoveMessage =>
        commandList = commandList.::(new MoveCommand(field, actPlayerName, posFrom, posTo))
      case preMessage: TileTrappedMessage =>
        commandList = commandList.::(new RemoveCommand(field, actPlayerName, posFrom, posTo))
    }

    // val posMessage: Option[MessageTrade] = Postcondition.isATileNoTrapped(field, PlayerNameEnum.GOLD, posFrom)
    // if (posMessage.isDefined)
    //   logger.info(posMessage.get.text)

    val action = new ActionCommand(commandList)

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
