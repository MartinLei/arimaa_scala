package model

import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import model.impl.{Player, Tile}
import util.Position

trait FieldTrait {
  def getPlayer(player: PlayerNameEnum): Player

  def isOccupied(pos: Position): Boolean

  def changeTilePos(player: PlayerNameEnum, posOld: Position, posNew: Position): Boolean

  def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum

  def getPlayerTiles(player: PlayerNameEnum): Set[Tile]
}
