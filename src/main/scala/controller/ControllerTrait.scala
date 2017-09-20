package controller

import controller.impl.ModeEnum.ModeEnum
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.position.Position

trait ControllerTrait {
  def changePlayer(): List[String]

  def moveTile(posFrom: Position, posTo: Position): List[String]

  def moveTileUndo(): List[String]

  def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum

  def getFieldAsString: String

  def setMode(mode: ModeEnum, field: FieldTrait): Boolean

  def getMode: ModeEnum
}
