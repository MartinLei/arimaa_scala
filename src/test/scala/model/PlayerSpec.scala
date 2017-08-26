package model

import model.impl.{Player, PlayerNameEnum, Tile, TileNameEnum}
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

  it should "have the given string representation" in {
    val playerString: String = "G:Set(R:{1,2})"

    val tiles: mutable.Set[Tile] = new mutable.HashSet()
    val t1: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 2))
    tiles add t1
    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)

    player.toString should be(playerString)
  }

  it should "say if has a tile on a given position" in {
    val tiles: mutable.Set[Tile] = new mutable.HashSet()
    val t1: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 2))
    tiles add t1

    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)

    player.isATileThere(new Position(1, 2)) should be(true)
    player.isATileThere(new Position(1, 3)) should be(false)
  }

  it should "give the name of the tile on a position if there is one" in {
    val tiles: mutable.Set[Tile] = new mutable.HashSet()
    val t1: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 1))
    tiles add t1
    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)

    player.getTileName(new Position(1, 1)) should be(TileNameEnum.RABBIT)
    player.getTileName(new Position(1, 2)) should be(TileNameEnum.NONE)
  }

  it should "be equal if the tile name and position is the same" in {
    val tiles1: mutable.Set[Tile] = new mutable.HashSet()
    val t1: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 2))
    tiles1 add t1
    val player1: Player = new Player(PlayerNameEnum.GOLD, tiles1)

    val tiles2: mutable.Set[Tile] = new mutable.HashSet()
    val t2: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 2))
    tiles2 add t2
    val player2: Player = new Player(PlayerNameEnum.GOLD, tiles2)

    player1 should be(player2)
  }

  it should "not be equal if the tile name or position is not the same" in {
    val tiles1: mutable.Set[Tile] = new mutable.HashSet()
    val t1: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 2))
    val t11: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 3))
    tiles1 add t1
    tiles1 add t11
    val player1: Player = new Player(PlayerNameEnum.GOLD, tiles1)

    val tiles2: mutable.Set[Tile] = new mutable.HashSet()
    val t2: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 2))
    tiles2 add t2
    val player2: Player = new Player(PlayerNameEnum.GOLD, tiles2)

    player1 should not be player2
  }

  it should "not be equal if its not a player object" in {
    val tiles1: mutable.Set[Tile] = new mutable.HashSet()
    val t1: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 2))
    val t11: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 3))
    tiles1 add t1
    tiles1 add t11
    val player1: Player = new Player(PlayerNameEnum.GOLD, tiles1)

    player1 should not be 1
  }

  it should "move a tile to a new position" in {
    val tiles: mutable.Set[Tile] = new mutable.HashSet()
    val t1: Tile = new Tile(TileNameEnum.RABBIT, new Position(1, 1))
    tiles add t1

    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)

    player.getTileName(new Position(1, 1)) should be(TileNameEnum.RABBIT)
    player.getTileName(new Position(1, 2)) should be(TileNameEnum.NONE)

    player.moveTile(new Position(1, 1), new Position(1, 2)) should be(true)

    player.getTileName(new Position(1, 1)) should be(TileNameEnum.NONE)
    player.getTileName(new Position(1, 2)) should be(TileNameEnum.RABBIT)
  }

}
