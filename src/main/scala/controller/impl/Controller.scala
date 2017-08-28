package controller.impl

import com.typesafe.scalalogging.Logger
import controller.ControllerTrait
import controller.impl.messages.MessageTrade
import controller.impl.messages.imp.OKMessage
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import model.impl.{Field, PlayerNameEnum}
import util.Position

class Controller extends ControllerTrait {
  private val logger = Logger[Controller]
  private val field: FieldTrait = new Field()
  private val ruleBook: RuleBook = new RuleBook(field)

  private var actPlayerName: PlayerNameEnum = PlayerNameEnum.GOLD

  override def getActPlayerName: PlayerNameEnum = actPlayerName

  override def getFieldAsString: String = {
    field.toString
  }

  override def moveTile(posFrom: Position, posTo: Position): MessageTrade = {

    val preMessage: MessageTrade = ruleBook.precondition(posFrom, posTo)
    if (!preMessage.valid)
      return preMessage


    //if (field.isOccupied(posTo))
    // return false

    field.changeTilePos(actPlayerName, posFrom, posTo)

    new OKMessage
  }

  override def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum = {
    field.getTileName(player, pos)
  }

  override def changePlayer(): Unit = {
    if (actPlayerName.equals(PlayerNameEnum.GOLD))
      actPlayerName = PlayerNameEnum.SILVER
    else
      actPlayerName = PlayerNameEnum.GOLD

    ruleBook.setRuleView(actPlayerName)
  }
}
