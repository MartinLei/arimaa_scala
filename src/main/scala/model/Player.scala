package model

import model.PlayerNameEnum.PlayerNameEnum
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


}
