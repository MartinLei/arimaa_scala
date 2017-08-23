package model

import com.typesafe.scalalogging.Logger
import model.TileNameEnum.TileNameEnum
import util.Position

class Tile(val name: TileNameEnum, val pos: Position) {
  val logger = Logger(classOf[Tile])
  logger.info("Hallo")

  override def toString: String = name.toString + "->" + pos.toString
}
