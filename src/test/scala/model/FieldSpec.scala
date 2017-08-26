package model

import org.scalatest.{FlatSpec, Matchers}
import util.Position

import scala.collection.mutable

class FieldSpec extends FlatSpec with Matchers {

  "A Field" should "have two player with a start tiles position" in {
    val tilesGoldShouldBe: mutable.Set[Tile] = new mutable.HashSet[Tile]
    tilesGoldShouldBe add new Tile(TileNameEnum.RABBIT, new Position(1, 1))

    val tilesSilverShouldBe: mutable.Set[Tile] = new mutable.HashSet[Tile]
    tilesSilverShouldBe add new Tile(TileNameEnum.RABBIT, new Position(1, 8))


    val field: Field = new Field()
    val tilesGold: mutable.Set[Tile] = field.getPlayerTiles(PlayerNameEnum.GOLD)
    tilesGold shouldEqual tilesGoldShouldBe


    val tilesSilver: mutable.Set[Tile] = field.getPlayerTiles(PlayerNameEnum.SILVER)
    tilesSilver should contain theSameElementsAs tilesSilverShouldBe

    //TODO finish with all figures
  }

  it should "give to a player and position the tale, if their is one" in {
    val field: Field = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 1)) should be(TileNameEnum.NONE)
  }

  it should "have a string representation of its field" in {
    val fieldString: String = "\n" +
      "  +-------------SILVER------------+\n" +
      "8 | R |   |   |   |   |   |   |   |\n" +
      "  +---+---+---+---+---+---+---+---+\n" +
      "7 |   |   |   |   |   |   |   |   |\n" +
      "  +---+---+---+---+---+---+---+---+\n" +
      "6 |   |   | # |   |   | # |   |   |\n" +
      "  +---+---+---+---+---+---+---+---+\n" +
      "5 |   |   |   |   |   |   |   |   |\n" +
      "  +---+---+---+---+---+---+---+---+\n" +
      "4 |   |   |   |   |   |   |   |   |\n" +
      "  +---+---+---+---+---+---+---+---+\n" +
      "3 |   |   | # |   |   | # |   |   |\n" +
      "  +---+---+---+---+---+---+---+---+\n" +
      "2 |   |   |   |   |   |   |   |   |\n" +
      "  +---+---+---+---+---+---+---+---+\n" +
      "1 | r |   |   |   |   |   |   |   |\n" +
      "  +-------------GOLD--------------+\n" +
      "    1   2   3   4   5   6   7   8  \n"

    val field: Field = new Field
    field.toString should be(fieldString)
  }
}
