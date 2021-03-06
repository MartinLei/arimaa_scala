package controller.impl.rule

import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PostConditionSpec extends FlatSpec with Matchers {

  "isTileTrapped" should "return true, if the tile is trapped" in {
    val field = new Field(Set(
      new Tile(TileNameEnum.RABBIT, new Position(3, 5)),
    ), Set())

    PostCondition.isTileTrapped(field, PlayerNameEnum.GOLD, new Position(3, 5), new Position(3, 6)) should be(true)
  }

  it should "false if pos is min. surround by one own tile" in {
    val field = new Field(Set(
      new Tile(TileNameEnum.RABBIT, new Position(6, 7)),
      new Tile(TileNameEnum.RABBIT, new Position(5, 6))
    ), Set())

    PostCondition.isTileTrapped(field, PlayerNameEnum.GOLD, new Position(5, 6), new Position(6, 6)) should be(false)
  }

  it should "false if pos is not a trap position" in {
    val field = new Field(Set(
      new Tile(TileNameEnum.RABBIT, new Position(4, 4)),
    ), Set())

    PostCondition.isTileTrapped(field, PlayerNameEnum.GOLD, new Position(4, 4), new Position(4, 5)) should be(false)
  }

  "isATileNowTrapped" should "the trap pos, if own figure stands on trap and is now not surround by any own tile" in {
    val field = new Field(Set(
      new Tile(TileNameEnum.RABBIT, new Position(3, 3)),
      new Tile(TileNameEnum.RABBIT, new Position(2, 3)),
    ), Set())

    PostCondition.isATileNowTrapped(field, new Position(3, 3)) should
      be(Option(null))

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 4))

    PostCondition.isATileNowTrapped(field, new Position(3, 3)) should
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

    PostCondition.isATileNowTrapped(field, new Position(2, 4)) should
      be(Some(new Position(3, 3)))
  }

  it should "null, if own figure stands on trap but still surround by one own tile" in {
    val field = new Field(Set(
      new Tile(TileNameEnum.RABBIT, new Position(3, 3)),
      new Tile(TileNameEnum.RABBIT, new Position(2, 3)),
    ), Set())

    PostCondition.isATileNowTrapped(field, new Position(3, 3)) should
      be(Option(null))
  }
  it should "null, if on tile is NONE tile" in {
    val field = new Field(Set(
      new Tile(TileNameEnum.RABBIT, new Position(2, 3))
    ), Set())

    PostCondition.isATileNowTrapped(field, new Position(2, 3)) should be(Option(null))
  }

}
