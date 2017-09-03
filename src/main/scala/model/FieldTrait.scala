package model

import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.Tile
import model.impl.TileNameEnum.TileNameEnum
import util.position.Position

trait FieldTrait {

  def isOccupied(pos: Position): Boolean

  def changeTilePos(player: PlayerNameEnum, posOld: Position, posNew: Position): Boolean

  def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum

  def getPlayerTiles(player: PlayerNameEnum): Set[Tile]

  def getStrongerOtherTilesWhoAround(player: PlayerNameEnum, pos: Position): Option[Position]

  def isHoledByOwnTile(pos: Position): Boolean

  def getPlayerName(pos: Position): PlayerNameEnum
}
