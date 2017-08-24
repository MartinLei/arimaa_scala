package model

import com.typesafe.scalalogging.Logger
import model.TileNameEnum.TileNameEnum
import util.Position

class Tile(val name: TileNameEnum, val pos: Position) {
  val logger = Logger(classOf[Tile])
  //  logger.info("Hallo")

  override def equals(that: scala.Any): Boolean = that match {
    case that: Tile => that.isInstanceOf[Tile] && that.hashCode() == this.hashCode()
    case _ => false
  }

  override def hashCode(): Int = {
    toString.hashCode()
  }

  override def toString: String = name.toString + ":" + pos.toString

}
