package controller.impl

import controller.ControllerTrait
import controller.impl.ModeEnum.ModeEnum
import controller.impl.command.TurnManager
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import model.impl.{Field, PlayerNameEnum}
import util.position.Position

class Controller extends ControllerTrait {
  private val field: FieldTrait = new Field()
  private val turnManager = new TurnManager(PlayerNameEnum.GOLD)
  private var mode = new GameMode(field, turnManager)

  override def setMode(modeEnum: ModeEnum, field: FieldTrait): Boolean = modeEnum match {
    case ModeEnum.GAME =>
      mode = new GameMode(field, turnManager)
      true
    case _ => false
  }

  override def getMode: ModeEnum = {
    if (mode.isInstanceOf[GameMode])
      return ModeEnum.GAME

    ModeEnum.NONE
  }

  override def getFieldAsString: String = {
    field.toString
  }

  override def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum = {
    field.getTileName(player, pos)
  }

  override def moveTile(posFrom: Position, posTo: Position): List[String] = {
    mode.moveTile(posFrom, posTo)
  }

  override def moveTileUndo(): List[String] = {
    mode.moveTileUndo()
  }

  override def changePlayer(): List[String] = {
    mode.changePlayer()
  }


}
