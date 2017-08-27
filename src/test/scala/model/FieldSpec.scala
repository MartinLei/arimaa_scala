package model

import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.Position

import scala.collection.mutable

class FieldSpec extends FlatSpec with Matchers {

  "A Field" should "have a gold 99of9 start tiles position" in {
    val tilesGoldShouldBe: mutable.Set[Tile] = new mutable.HashSet[Tile]
    tilesGoldShouldBe add new Tile(TileNameEnum.RABBIT, new Position(1, 1))
    tilesGoldShouldBe add new Tile(TileNameEnum.RABBIT, new Position(2, 1))
    tilesGoldShouldBe add new Tile(TileNameEnum.RABBIT, new Position(3, 1))
    tilesGoldShouldBe add new Tile(TileNameEnum.DOG, new Position(4, 1))
    tilesGoldShouldBe add new Tile(TileNameEnum.DOG, new Position(5, 1))
    tilesGoldShouldBe add new Tile(TileNameEnum.RABBIT, new Position(6, 1))
    tilesGoldShouldBe add new Tile(TileNameEnum.RABBIT, new Position(7, 1))
    tilesGoldShouldBe add new Tile(TileNameEnum.RABBIT, new Position(8, 1))
    tilesGoldShouldBe add new Tile(TileNameEnum.RABBIT, new Position(1, 2))
    tilesGoldShouldBe add new Tile(TileNameEnum.HORSE, new Position(2, 2))
    tilesGoldShouldBe add new Tile(TileNameEnum.CAT, new Position(3, 2))
    tilesGoldShouldBe add new Tile(TileNameEnum.CAMEL, new Position(4, 2))
    tilesGoldShouldBe add new Tile(TileNameEnum.ELEPHANT, new Position(5, 2))
    tilesGoldShouldBe add new Tile(TileNameEnum.CAT, new Position(6, 2))
    tilesGoldShouldBe add new Tile(TileNameEnum.HORSE, new Position(7, 2))
    tilesGoldShouldBe add new Tile(TileNameEnum.RABBIT, new Position(8, 2))

    val field: FieldTrait = new Field()
    val tilesGold: mutable.Set[Tile] = field.getPlayerTiles(PlayerNameEnum.GOLD)

    tilesGold shouldEqual tilesGoldShouldBe
  }

  it should "have a silver 99of9 start tiles position" in {
    val tilesSilverShouldBe: mutable.Set[Tile] = new mutable.HashSet[Tile]
    tilesSilverShouldBe add new Tile(TileNameEnum.RABBIT, new Position(1, 8))
    tilesSilverShouldBe add new Tile(TileNameEnum.RABBIT, new Position(2, 8))
    tilesSilverShouldBe add new Tile(TileNameEnum.RABBIT, new Position(3, 8))
    tilesSilverShouldBe add new Tile(TileNameEnum.DOG, new Position(4, 8))
    tilesSilverShouldBe add new Tile(TileNameEnum.DOG, new Position(5, 8))
    tilesSilverShouldBe add new Tile(TileNameEnum.RABBIT, new Position(6, 8))
    tilesSilverShouldBe add new Tile(TileNameEnum.RABBIT, new Position(7, 8))
    tilesSilverShouldBe add new Tile(TileNameEnum.RABBIT, new Position(8, 8))
    tilesSilverShouldBe add new Tile(TileNameEnum.RABBIT, new Position(1, 7))
    tilesSilverShouldBe add new Tile(TileNameEnum.HORSE, new Position(2, 7))
    tilesSilverShouldBe add new Tile(TileNameEnum.CAT, new Position(3, 7))
    tilesSilverShouldBe add new Tile(TileNameEnum.ELEPHANT, new Position(4, 7))
    tilesSilverShouldBe add new Tile(TileNameEnum.CAMEL, new Position(5, 7))
    tilesSilverShouldBe add new Tile(TileNameEnum.CAT, new Position(6, 7))
    tilesSilverShouldBe add new Tile(TileNameEnum.HORSE, new Position(7, 7))
    tilesSilverShouldBe add new Tile(TileNameEnum.RABBIT, new Position(8, 7))

    val field: FieldTrait = new Field()
    val tilesSilver: mutable.Set[Tile] = field.getPlayerTiles(PlayerNameEnum.SILVER)
    tilesSilver shouldEqual tilesSilverShouldBe
  }

  it should "give to a player and position the tale, if their is one" in {
    val field: FieldTrait = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 1)) should be(TileNameEnum.NONE)
  }

  "toString" should "have given output" in {
    val field99of9String: String = "\n" +
      "  +-----------------+\n" +
      "8 | r r r d d r r r |\n" +
      "7 | r h c e m c h r |\n" +
      "6 |     X     X     |\n" +
      "5 |                 |\n" +
      "4 |                 |\n" +
      "3 |     X     X     |\n" +
      "2 | R H C M E C H R |\n" +
      "1 | R R R D D R R R |\n" +
      "  +-----------------+\n" +
      "    a b c d e f g h  \n"

    val field: FieldTrait = new Field
    field.toString should be(field99of9String)
  }

  "move" should "move a gold tile with given position" in {
    val field: FieldTrait = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    field.moveTile(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)) should be(true)

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
  }
  it should "move a silver tile with given position" in {
    val field: FieldTrait = new Field()

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 6)) should be(TileNameEnum.NONE)

    field.moveTile(PlayerNameEnum.SILVER, new Position(1, 7), new Position(1, 6)) should be(true)

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 6)) should be(TileNameEnum.RABBIT)
  }
}

