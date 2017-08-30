package model.impl

import model.impl.TileNameEnum.TileNameEnum
import util.position.Position

class Tile(val name: TileNameEnum, val pos: Position) {

  override def equals(that: scala.Any): Boolean = that match {
    case that: Tile => that.isInstanceOf[Tile] && that.hashCode() == this.hashCode()
    case _ => false
  }

  override def hashCode(): Int = {
    toString.hashCode()
  }

  override def toString: String = name.toString + ":" + pos.toString

}
