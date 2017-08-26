package model.impl

import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.Position

import scala.collection.mutable


class Player(val name: PlayerNameEnum, newTiles: mutable.Set[Tile]) {
  private val tiles: mutable.Set[Tile] = newTiles.clone()


  def getTiles: mutable.Set[Tile] = {
    tiles
  }

  def isATileThere(pos: Position): Boolean = {
    tiles.exists { t: Tile => t.pos.equals(pos) }
  }

  def getTile(pos: Position): TileNameEnum = {
    val tileFiltered: mutable.Set[Tile] = tiles.filter { t: Tile => t.pos.equals(pos) }
    if (tileFiltered.isEmpty)
      return TileNameEnum.NONE

    tileFiltered.head.name
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

}
