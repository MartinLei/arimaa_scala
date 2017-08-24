package model

import org.scalatest.{FlatSpec, Matchers}
import util.Position

import scala.collection.mutable

class FieldSpec extends FlatSpec with Matchers {

  "A Field" should "have two player with a start tiles position" in {
    val tilesGoldShouldBe: mutable.Set[Tile] = new mutable.HashSet[Tile]
    tilesGoldShouldBe add new Tile(TileNameEnum.RABBIT, new Position(1, 1))

    val tilesSilverShouldBe: mutable.Set[Tile] = new mutable.HashSet[Tile]
    tilesSilverShouldBe add new Tile(TileNameEnum.RABBIT, new Position(1, 6))


    val field: Field = new Field()
    val tilesGold: mutable.Set[Tile] = field.getPlayerTiles(PlayerNameEnum.GOLD)
    tilesGold shouldEqual tilesGoldShouldBe


    val tilesSilver: mutable.Set[Tile] = field.getPlayerTiles(PlayerNameEnum.SILVER)
    tilesSilver should contain theSameElementsAs tilesSilverShouldBe

    //TODO finish with all figures
  }

  it should "have a string representation of its field" in {
    //TODO
  }
}
