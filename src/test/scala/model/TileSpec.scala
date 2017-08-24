package model

import org.scalatest._
import util.Position


class TileSpec extends FlatSpec with Matchers {

  "A Tile" should "have a name and a position" in {
    val tileName = TileNameEnum.CAT
    val tilePos = new Position(1, 2)
    val tile: Tile = new Tile(tileName, tilePos)

    tile.name should be(tileName)
    tile.pos should be(tilePos)
  }
  it should "have a string representation" in {
    val tileName = TileNameEnum.CAT
    val tilePos = new Position(1, 2)
    val tile: Tile = new Tile(tileName, tilePos)
    val tileString = "C:{1,2}"

    tile.toString should be(tileString)
  }

  it should "be the same object if name and position is equal" in {
    val tile1: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 1))
    val tile2: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 1))

    tile1 should be(tile2)
  }

  it should "be not the same object if name or position is not equal" in {
    val tile1: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 1))
    val tile2: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 2))

    tile1 should not be tile2
  }
}
