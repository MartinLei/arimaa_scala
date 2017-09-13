package controller

import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.position.Position

trait ControllerTrait {
  def changePlayer(): List[String]

  def getActPlayerName: PlayerNameEnum

  def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum

  def moveTile(posFrom: Position, posTo: Position): List[String]

  def moveTileUndo(): List[String]

  def getFieldAsString: String

}
