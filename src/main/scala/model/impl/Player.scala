package model.impl

import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.position.Position


class Player(val name: PlayerNameEnum, newTiles: Set[Tile]) {
  private var tiles: Set[Tile] = newTiles


  def getTiles: Set[Tile] = {
    tiles
  }

  def isATileThere(pos: Position): Boolean = {
    tiles.exists { t: Tile => t.pos.equals(pos) }
  }

  def getTileName(pos: Position): TileNameEnum = {
    val tileFiltered: Set[Tile] = tiles.filter { t: Tile => t.pos.equals(pos) }
    if (tileFiltered.isEmpty)
      return TileNameEnum.NONE

    tileFiltered.head.name
  }

  def moveTile(posOld: Position, posNew: Position): Boolean = {
    val tileFiltered: Option[Tile] = tiles.find { t: Tile => t.pos.equals(posOld) }

    if (tileFiltered.isEmpty)
      return false


    val tile: Tile = tileFiltered.get
    tiles = tiles - tile
    tiles = tiles + new Tile(tile.name, posNew)
    true
  }

  override def equals(that: scala.Any): Boolean = that match {
    case that: Player => that.isInstanceOf[Player] && that.hashCode() == this.hashCode()
    case _ => false
  }

  override def hashCode(): Int = {
    toString.hashCode
  }

  override def toString: String = {
    name.toString + ":" + tiles.toString()
  }

  def remove(pos: Position): Boolean = {
    if (!isATileThere(pos))
      return false

    tiles = tiles.filter(tile => !tile.pos.equals(pos))
    true
  }

  def add(tileName: TileNameEnum, pos: Position): Boolean = {
    if (isATileThere(pos))
      return false

    val tile = new Tile(tileName, pos)
    tiles = tiles.+(tile)
    true
  }

  def hasNoRabbits: Boolean = {
    val rabbitCount = tiles.count(tile => tile.name.equals(TileNameEnum.RABBIT))

    rabbitCount == 0
  }

  def hasRabbitOnRow(row: Int): Boolean = {
    if (row > 8 || row < 1)
      return false

    val rabbitOnRowCount = tiles.count(tile => {
      tile.name.equals(TileNameEnum.RABBIT) && tile.pos.isPosYEquals(row)
    })

    rabbitOnRowCount > 0
  }
}
