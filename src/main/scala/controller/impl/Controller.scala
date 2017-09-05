package controller.impl

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import controller.impl.command.UndoManager
import controller.impl.command.impl.{MoveCommand, RemoveCommand}
import controller.impl.messages.MessageTrade
import controller.impl.messages.impl.{MoveMessage, TileTrappedMessage}
import controller.rule.{Postcondition, RuleBook}
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
  private val undoManager = new UndoManager

  override def getActPlayerName: PlayerNameEnum = actPlayerName

  override def getFieldAsString: String = {
    field.toString
  }

  override def moveTile(posFrom: Position, posTo: Position): MessageTrade = {
    val preMessage: MessageTrade = ruleBook.isMoveRuleComplaint(actPlayerName, posFrom, posTo)
    if (!preMessage.valid)
      return preMessage

    preMessage match {
      case preMessage: MoveMessage =>
        val moveCommand = new MoveCommand(field, actPlayerName, posFrom, posTo)
        undoManager.doCommand(moveCommand)

      case preMessage: TileTrappedMessage =>
        val removeCommand = new RemoveCommand(field, actPlayerName, posFrom, posTo)
        undoManager.doCommand(removeCommand)

    }

    val posMessage: Option[MessageTrade] = Postcondition.isATileNoTrapped(field, PlayerNameEnum.GOLD, posFrom)
    if (posMessage.isDefined)
      logger.info(posMessage.get.text)

    preMessage
  }

  override def moveTileUndo(): MessageTrade = {
    undoManager.undoCommand()
  }

  override def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum = {
    field.getTileName(player, pos)
  }

  override def changePlayer(): Unit = {
    actPlayerName = PlayerNameEnum.getInvertPlayer(actPlayerName)
  }

}
