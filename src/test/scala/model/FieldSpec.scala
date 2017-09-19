package model

import model.impl.{Tile, _}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class FieldSpec extends FlatSpec with Matchers {

  "A Field" should "have a gold 99of9 start tiles position" in {
    val tilesGoldShouldBe: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(2, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(3, 1)),
      new Tile(TileNameEnum.DOG, new Position(4, 1)),
      new Tile(TileNameEnum.DOG, new Position(5, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(6, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(7, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(8, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)),
      new Tile(TileNameEnum.HORSE, new Position(2, 2)),
      new Tile(TileNameEnum.CAT, new Position(3, 2)),
      new Tile(TileNameEnum.CAMEL, new Position(4, 2)),
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 2)),
      new Tile(TileNameEnum.CAT, new Position(6, 2)),
      new Tile(TileNameEnum.HORSE, new Position(7, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)))

    val field: FieldTrait = new Field()
    val tilesGold: Set[Tile] = field.getPlayerTiles(PlayerNameEnum.GOLD)

    tilesGold shouldEqual tilesGoldShouldBe
  }
  it should "have a silver 99of9 start tiles position" in {
    val tilesSilverShouldBe: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)),
      new Tile(TileNameEnum.RABBIT, new Position(2, 8)),
      new Tile(TileNameEnum.RABBIT, new Position(3, 8)),
      new Tile(TileNameEnum.DOG, new Position(4, 8)),
      new Tile(TileNameEnum.DOG, new Position(5, 8)),
      new Tile(TileNameEnum.RABBIT, new Position(6, 8)),
      new Tile(TileNameEnum.RABBIT, new Position(7, 8)),
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)),
      new Tile(TileNameEnum.HORSE, new Position(2, 7)),
      new Tile(TileNameEnum.CAT, new Position(3, 7)),
      new Tile(TileNameEnum.ELEPHANT, new Position(4, 7)),
      new Tile(TileNameEnum.CAMEL, new Position(5, 7)),
      new Tile(TileNameEnum.CAT, new Position(6, 7)),
      new Tile(TileNameEnum.HORSE, new Position(7, 7)),
      new Tile(TileNameEnum.RABBIT, new Position(8, 7)))

    val field: FieldTrait = new Field()
    val tilesSilver: Set[Tile] = field.getPlayerTiles(PlayerNameEnum.SILVER)
    tilesSilver shouldEqual tilesSilverShouldBe
  }
  it should "init with other tiles" in {
    val playerGoldTiles = Set(new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    field.getPlayerTiles(PlayerNameEnum.GOLD) shouldEqual playerGoldTiles
    field.getPlayerTiles(PlayerNameEnum.SILVER) shouldEqual playerSilverTiles
  }

  it should "give a empty set if player is NONE" in {
    val field: FieldTrait = new Field()
    field.getPlayerTiles(PlayerNameEnum.NONE) should be(Set())
  }

  "getTileName" should "give to a player and position the tale, if their is one" in {
    val field: FieldTrait = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 1)) should be(TileNameEnum.NONE)
  }

  it should "give NONE if player is NONE" in {
    val field: FieldTrait = new Field()
    field.getTileName(PlayerNameEnum.NONE, new Position(1, 1)) should be(TileNameEnum.NONE)
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

    val field = new Field
    field.toString should be(field99of9String)
  }
  it should "show tile on trap if its hold" in {
    val fieldString: String = "\n" +
      "  +-----------------+\n" +
      "8 | r r r d d r r r |\n" +
      "7 | r h c e m c h r |\n" +
      "6 |     X     X     |\n" +
      "5 |                 |\n" +
      "4 |                 |\n" +
      "3 |   H C     X     |\n" +
      "2 | R     M E C H R |\n" +
      "1 | R R R D D R R R |\n" +
      "  +-----------------+\n" +
      "    a b c d e f g h  \n"

    val field = new Field
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(3, 2), new Position(3, 3))
    field.toString should be(fieldString)
  }

  "isOccupied" should "tell if a cell is occupied" in {
    val field: FieldTrait = new Field()
    field.isOccupied(new Position(1, 2)) should be(true)
    field.isOccupied(new Position(1, 3)) should be(false)

    field.isOccupied(new Position(1, 7)) should be(true)
    field.isOccupied(new Position(1, 6)) should be(false)
  }

  "changeTilePos" should "change the position of a gold tile" in {
    val field: FieldTrait = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)) should be(true)

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
  }
  it should "change the position of a silver tile" in {
    val field: FieldTrait = new Field()

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 6)) should be(TileNameEnum.NONE)

    field.changeTilePos(PlayerNameEnum.SILVER, new Position(1, 7), new Position(1, 6)) should be(true)

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 6)) should be(TileNameEnum.RABBIT)
  }
  it should "be false if player is NONE" in {
    val field: FieldTrait = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    field.changeTilePos(PlayerNameEnum.NONE, new Position(1, 2), new Position(1, 3)) should be(false)

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
  }
  it should "be false if position is already occupied" in {
    val field: FieldTrait = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)

    field.changeTilePos(PlayerNameEnum.NONE, new Position(1, 1), new Position(1, 2)) should be(false)

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.RABBIT)

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 7)) should be(false)

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.RABBIT)
  }

  "getStrongerTilesWhoAround" should "get the pos of the stronger other player tile who surround the tile" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(4, 2), new Position(4, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(4, 7), new Position(4, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAMEL)
    field.getTileName(PlayerNameEnum.SILVER, new Position(4, 5)) should be(TileNameEnum.ELEPHANT)

    field.getStrongerTilesWhoAround(PlayerNameEnum.SILVER, new Position(4, 4), PlayerNameEnum.GOLD) should
      be(List(new Position(4, 5)))
  }
  it should "get pos of stronger own tile surround" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)

    field.getStrongerTilesWhoAround(PlayerNameEnum.GOLD, new Position(1, 2), PlayerNameEnum.GOLD) should
      be(List(new Position(2, 2)))
  }
  it should "be empty if surround tile weaker than own" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(4, 2), new Position(4, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(1, 7), new Position(4, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAMEL)
    field.getTileName(PlayerNameEnum.SILVER, new Position(4, 5)) should be(TileNameEnum.RABBIT)

    field.getStrongerTilesWhoAround(PlayerNameEnum.SILVER, new Position(4, 2), PlayerNameEnum.GOLD) should
      be(List())
  }
  it should "be empty if it not surround" in {
    val field = new Field()
    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 2)) should be(TileNameEnum.CAMEL)

    field.getStrongerTilesWhoAround(PlayerNameEnum.SILVER, new Position(4, 2), PlayerNameEnum.GOLD) should
      be(List())
  }
  it should "be empty if player around is NONE" in {
    val field = new Field()
    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 2)) should be(TileNameEnum.CAMEL)

    field.getStrongerTilesWhoAround(PlayerNameEnum.NONE, new Position(4, 2), PlayerNameEnum.GOLD) should
      be(List())
  }
  it should "be empty if player pos is NONE" in {
    val field = new Field()
    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 2)) should be(TileNameEnum.CAMEL)

    field.getStrongerTilesWhoAround(PlayerNameEnum.GOLD, new Position(4, 2), PlayerNameEnum.NONE) should
      be(List())
  }

  "isSurroundByOwnTile" should "be true if a own tile is around" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    field.isSurroundByOwnTile(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)) should be(true)
  }
  it should "be false if not surround by own tile" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    field.isSurroundByOwnTile(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)) should be(false)
  }
  it should "be false if posFrom is the only surround posTo" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    field.isSurroundByOwnTile(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)) should be(false)
  }
  it should "be false if player is NONE" in {
    val field = new Field()

    field.isSurroundByOwnTile(PlayerNameEnum.NONE, new Position(1, 2), new Position(1, 3)) should be(false)
  }

  "getPlayerName" should "give the player name of the tile if there is one" in {
    val field = new Field()
    field.getPlayerName(new Position(1, 1)) should be(PlayerNameEnum.GOLD)
    field.getPlayerName(new Position(1, 8)) should be(PlayerNameEnum.SILVER)
  }
  it should "be NONE if there is no tile" in {
    val field = new Field()
    field.getPlayerName(new Position(1, 5)) should be(PlayerNameEnum.NONE)
  }

  "removeTile" should "remove the tile from player on this position" in {
    val field = new Field()
    field.getPlayerName(new Position(1, 1)) should be(PlayerNameEnum.GOLD)
    field.removeTile(new Position(1, 1))
    field.getPlayerName(new Position(1, 1)) should be(PlayerNameEnum.NONE)
  }

  it should "do nothing if the player is NONE" in {
    val field = new Field()
    field.getPlayerName(new Position(1, 3)) should be(PlayerNameEnum.NONE)
    field.removeTile(new Position(1, 3))
    field.getPlayerName(new Position(1, 3)) should be(PlayerNameEnum.NONE)
  }

  "addTile" should "add the tile to the given player" in {
    val field = new Field()
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    field.addTile(PlayerNameEnum.GOLD, TileNameEnum.RABBIT, new Position(1, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 6)) should be(TileNameEnum.NONE)
    field.addTile(PlayerNameEnum.SILVER, TileNameEnum.RABBIT, new Position(1, 6))
    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 6)) should be(TileNameEnum.RABBIT)
  }
  it should "do nothing if player is NONE" in {
    val field = new Field()
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 5)) should be(TileNameEnum.NONE)
    field.addTile(PlayerNameEnum.NONE, TileNameEnum.RABBIT, new Position(1, 5))
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 5)) should be(TileNameEnum.NONE)
  }
  it should "do nothing if tile is already occupied by other player" in {
    val field = new Field()
    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.addTile(PlayerNameEnum.SILVER, TileNameEnum.RABBIT, new Position(1, 2))
    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 2)) should be(TileNameEnum.NONE)
  }

  "changePlayer" should "change player form gold to silver" in {
    val field = new Field()
    field.actualPlayerName should be(PlayerNameEnum.GOLD)
    field.changePlayer() should be(PlayerNameEnum.SILVER)
    field.actualPlayerName should be(PlayerNameEnum.SILVER)
    field.changePlayer() should be(PlayerNameEnum.GOLD)
    field.actualPlayerName should be(PlayerNameEnum.GOLD)
  }

  "hasNoRabbit" should "true if player has no rabbits" in {
    val field = new Field(Set(), Set())

    field.hasNoRabbits(PlayerNameEnum.GOLD) should be(true)
    field.hasNoRabbits(PlayerNameEnum.SILVER) should be(true)
  }
  it should "false if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    field.hasNoRabbits(PlayerNameEnum.GOLD) should be(false)
    field.hasNoRabbits(PlayerNameEnum.SILVER) should be(false)
  }
  it should "false if player NONE" in {
    val field = new Field()
    field.hasNoRabbits(PlayerNameEnum.NONE) should be(false)
  }

  "hasRabbitOnOtherSide" should "true if player has one on the other side" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 1)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    field.hasRabbitOnOtherSide(PlayerNameEnum.GOLD) should be(true)
    field.hasRabbitOnOtherSide(PlayerNameEnum.SILVER) should be(true)
  }
  it should "false if its player NONE" in {
    val field = new Field(Set(), Set())
    field.hasRabbitOnOtherSide(PlayerNameEnum.NONE) should be(false)
  }
}

