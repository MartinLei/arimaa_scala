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

  def getStrongerTilesWhoAround(playerAround: PlayerNameEnum, pos: Position, playerPos: PlayerNameEnum): List[Position]

  def isSurroundByOwnTile(player: PlayerNameEnum, posFrom_Ignore: Position, posTo_Observe: Position): Boolean

  def getPlayerName(pos: Position): PlayerNameEnum

  def removeTile(pos: Position): Unit

  def addTile(playerName: PlayerNameEnum, tileName: TileNameEnum, pos: Position): Unit

  var actualPlayerName: PlayerNameEnum

  def changePlayer(): Unit

  var winPlayerName: PlayerNameEnum
}
