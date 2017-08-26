package controller

import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.Position

trait ControllerTrait {
  def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum

  def moveTile(posFrom: Position, posTo: Position): Boolean

  def getFieldAsString: String
}
