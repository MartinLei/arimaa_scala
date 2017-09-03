package model

import model.impl.{Player, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PlayerSpec extends FlatSpec with Matchers {

  "A Player" should "have a name" in {
    val player: Player = new Player(PlayerNameEnum.GOLD, Set())
    player.name should be(PlayerNameEnum.GOLD)
  }

  it should "have a set of tales" in {
    val tiles: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)),
      new Tile(TileNameEnum.HORSE, new Position(1, 3)))

    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)

    player.getTiles should contain theSameElementsAs tiles
  }

  "toString" should "have given output" in {
    val playerString: String = "G:Set(R:{1,2})"

    val tiles: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)

    player.toString should be(playerString)
  }

  it should "say if has a tile on a given position" in {
    val tiles: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))

    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)

    player.isATileThere(new Position(1, 2)) should be(true)
    player.isATileThere(new Position(1, 3)) should be(false)
  }

  it should "give the name of the tile on a position if there is one" in {
    val tiles: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)

    player.getTileName(new Position(1, 1)) should be(TileNameEnum.RABBIT)
    player.getTileName(new Position(1, 2)) should be(TileNameEnum.NONE)
  }

  "equal" should "objects, if the tile name and position is the same" in {
    val tiles1: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val player1: Player = new Player(PlayerNameEnum.GOLD, tiles1)

    val tiles2: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val player2: Player = new Player(PlayerNameEnum.GOLD, tiles2)

    player1 should be(player2)
  }

  it should "not equal, if the tile name or position is different" in {
    val tiles1: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 3)))
    val player1: Player = new Player(PlayerNameEnum.GOLD, tiles1)

    val tiles2: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val player2: Player = new Player(PlayerNameEnum.GOLD, tiles2)

    player1 should not be player2
  }

  it should "not equal, if its not a player object" in {
    val tiles1: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 3)))
    val player1: Player = new Player(PlayerNameEnum.GOLD, tiles1)

    player1 should not be 1
  }

  "move" should "move a tile to a new position" in {
    val tiles: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)

    player.getTileName(new Position(1, 1)) should be(TileNameEnum.RABBIT)
    player.getTileName(new Position(1, 2)) should be(TileNameEnum.NONE)

    player.moveTile(new Position(1, 1), new Position(1, 2)) should be(true)

    player.getTileName(new Position(1, 1)) should be(TileNameEnum.NONE)
    player.getTileName(new Position(1, 2)) should be(TileNameEnum.RABBIT)
  }

  it should "be false if on given position is no tile" in {
    val tiles: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))


    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)

    player.getTileName(new Position(1, 1)) should be(TileNameEnum.RABBIT)
    player.getTileName(new Position(1, 2)) should be(TileNameEnum.NONE)

    player.moveTile(new Position(1, 2), new Position(1, 1)) should be(false)

    player.getTileName(new Position(1, 1)) should be(TileNameEnum.RABBIT)
    player.getTileName(new Position(1, 2)) should be(TileNameEnum.NONE)
  }

  "remove" should "remove tile on given position" in {
    val tiles: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))

    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)
    player.remove(new Position(1, 1))
    player.getTileName(new Position(1, 1)) should be(TileNameEnum.NONE)
  }

  it should "do nothing if tile pos not in set" in {
    val tiles: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))

    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)
    player.remove(new Position(2, 2))
    player.getTileName(new Position(1, 1)) should be(TileNameEnum.RABBIT)
  }

  "add" should "add the given tile on the position" in {
    val tiles: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))

    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)
    player.add(TileNameEnum.RABBIT, new Position(1, 3))

    player.getTileName(new Position(1, 3)) should be(TileNameEnum.RABBIT)
  }
  it should "do nothing if the position is already occupied" in {
    val tiles: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))

    val player: Player = new Player(PlayerNameEnum.GOLD, tiles)
    player.add(TileNameEnum.RABBIT, new Position(1, 1))

    player.getTileName(new Position(1, 1)) should be(TileNameEnum.RABBIT)
    player.getTiles should contain theSameElementsAs tiles
  }
}
