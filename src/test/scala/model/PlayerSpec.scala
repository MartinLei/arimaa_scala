package model

import org.scalatest.{FlatSpec, Matchers}
import util.Position

import scala.collection.mutable

class PlayerSpec extends FlatSpec with Matchers {

  "A Player" should "have a name" in {
    val player: Player = new Player(PlayerNameEnum.GOLD, new mutable.HashSet())
    player.name should be(PlayerNameEnum.GOLD)
  }

  it should "have a set of tales" in {
    val tiles: mutable.Set[Tile] = new mutable.HashSet()
    val t1: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 2))
    val t2: Tile = new Tile(TileNameEnum.HORSE, new Position(1, 3))
    tiles add t1
    tiles add t2

    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)

    player.getTiles should contain theSameElementsAs tiles
  }

  it should "say if has a tile on a given position" in {
    val tiles: mutable.Set[Tile] = new mutable.HashSet()
    val t1: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 2))
    tiles add t1

    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)

    player.isATileThere(new Position(1, 2)) should be(true)
    player.isATileThere(new Position(1, 3)) should be(false)
  }


}
