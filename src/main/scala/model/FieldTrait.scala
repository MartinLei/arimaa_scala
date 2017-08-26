package model

import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.Tile
import model.impl.TileNameEnum.TileNameEnum
import util.Position

import scala.collection.mutable

trait FieldTrait {
  def moveTile(player: PlayerNameEnum, posOld: Position, posNew: Position): Boolean

  def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum

  def getPlayerTiles(player: PlayerNameEnum): mutable.Set[Tile]
}
