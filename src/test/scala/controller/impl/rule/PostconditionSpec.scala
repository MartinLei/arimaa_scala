package controller.impl.rule

import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PostconditionSpec extends FlatSpec with Matchers {

  "isTileTrapped" should "return true, if the tail is trapped" in {
    val field = new Field(Set(
      new Tile(TileNameEnum.RABBIT, new Position(3, 5)),
    ), Set())

    Postcondition.isTileTrapped(field, PlayerNameEnum.GOLD, new Position(3, 5), new Position(3, 6)) should be(true)
  }

  it should "false if pos is min. surround by one own tile" in {
    val field = new Field(Set(
      new Tile(TileNameEnum.RABBIT, new Position(6, 7)),
      new Tile(TileNameEnum.RABBIT, new Position(5, 6))
    ), Set())

    Postcondition.isTileTrapped(field, PlayerNameEnum.GOLD, new Position(5, 6), new Position(6, 6)) should be(false)
  }

  it should "false if pos is not a trap position" in {
    val field = new Field(Set(
      new Tile(TileNameEnum.RABBIT, new Position(4, 4)),
    ), Set())

    Postcondition.isTileTrapped(field, PlayerNameEnum.GOLD, new Position(4, 4), new Position(4, 5)) should be(false)
  }

  "isATileNowTrapped" should "the trap pos, if own figure stands on trap and is now not surround by any own tile" in {
    val field = new Field(Set(
      new Tile(TileNameEnum.RABBIT, new Position(3, 3)),
      new Tile(TileNameEnum.RABBIT, new Position(2, 3)),
    ), Set())

    Postcondition.isATileNowTrapped(field, new Position(3, 3)) should
      be(Option(null))

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 4))

    Postcondition.isATileNowTrapped(field, new Position(3, 3)) should
      be(Some(new Position(3, 3)))
  }
  it should "the trap pos, if trap is a tile and surround tile gets pulled" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(2, 3)),
      new Tile(TileNameEnum.CAT, new Position(3, 3)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(2, 4)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    field.changeTilePos(PlayerNameEnum.SILVER, new Position(2, 4), new Position(3, 4))
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 4))

    Postcondition.isATileNowTrapped(field, new Position(2, 4)) should
      be(Some(new Position(3, 3)))
  }

  it should "null, if own figure stands on trap but still surround by one own tile" in {
    val field = new Field(Set(
      new Tile(TileNameEnum.RABBIT, new Position(3, 3)),
      new Tile(TileNameEnum.RABBIT, new Position(2, 3)),
    ), Set())

    Postcondition.isATileNowTrapped(field, new Position(3, 3)) should
      be(Option(null))
  }
  it should "null, if on tile is NONE tile" in {
    val field = new Field(Set(
      new Tile(TileNameEnum.RABBIT, new Position(2, 3))
    ), Set())

    Postcondition.isATileNowTrapped(field, new Position(2, 3)) should be(Option(null))
  }

  "isRabbitReachedGoal" should "return the player hows tile is rabbit and posTo is on other field end" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    Postcondition.isRabbitReachedGoal(field, new Position(1, 7), new Position(1, 8)) should be(PlayerNameEnum.GOLD)

    Postcondition.isRabbitReachedGoal(field, new Position(8, 2), new Position(8, 1)) should
      be(PlayerNameEnum.SILVER)
  }
  it should "be NONE if not rabbit ore not on other side" in {
    val field = new Field(Set(), Set())
    Postcondition.isRabbitReachedGoal(field, new Position(2, 5), new Position(2, 6)) should be(PlayerNameEnum.NONE)
    Postcondition.isRabbitReachedGoal(field, new Position(1, 7), new Position(1, 8)) should be(PlayerNameEnum.NONE)
  }


  "winByKillAllOtherRabbits" should "gold if silver has no rabbits on field" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val field = new Field(playerGoldTiles, Set())

    Postcondition.winByKillAllOtherRabbits(field) should be(PlayerNameEnum.GOLD)
  }
  it should "silver if gold has no rabbits on field" in {
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)))
    val field = new Field(Set(), playerSilverTiles)

    field.changePlayer()

    Postcondition.winByKillAllOtherRabbits(field) should be(PlayerNameEnum.SILVER)
  }
  it should "NONE if gold and silver has a rabbit" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    Postcondition.winByKillAllOtherRabbits(field) should be(PlayerNameEnum.NONE)
  }
}
