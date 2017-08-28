package controller.impl

import controller.ControllerTrait
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import model.impl.{Field, PlayerNameEnum}
import util.Position

class Controller extends ControllerTrait {
  private val field: FieldTrait = new Field()
  private val ruleBook: RuleBook = new RuleBook(field)

  private var actPlayerName: PlayerNameEnum = PlayerNameEnum.GOLD

  override def getActPlayerName: PlayerNameEnum = actPlayerName

  override def getFieldAsString: String = {
    field.toString
  }

  override def moveTile(posFrom: Position, posTo: Position): Boolean = {

    if (!ruleBook.precondition(posFrom, posTo))
      return false

    //if (field.isOccupied(posTo))
    // return false

    field.changeTilePos(actPlayerName, posFrom, posTo)
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
