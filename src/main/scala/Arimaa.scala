import com.typesafe.scalalogging.Logger
import model.TileNameEnum
import model.imp.Tile
import util.Position

object Arimaa {
  val logger = Logger("Arimaa")

  def main(ags: Array[String]): Unit = {
    logger.info("Arimaa")

    val tile1pos: Position = new Position(1, 2)
    val tile1: Tile = new Tile(TileNameEnum.CAMEL, tile1pos)
    logger.info(tile1.toString)
  }
}
