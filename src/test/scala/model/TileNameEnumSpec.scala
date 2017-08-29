package model

import model.impl.TileNameEnum
import org.scalatest._

class TileNameEnumSpec extends FlatSpec with Matchers {

  "The TileName" should "have this elements with string representation" in {
    TileNameEnum.NONE.toString should be("NONE")

    TileNameEnum.RABBIT.toString should be("R")
    TileNameEnum.CAT.toString should be("C")
    TileNameEnum.DOG.toString should be("D")
    TileNameEnum.HORSE.toString should be("H")
    TileNameEnum.CAMEL.toString should be("M")
    TileNameEnum.ELEPHANT.toString should be("E")
  }

  it should "have the given strength hierarchy" in {
    TileNameEnum.NONE.id should be(0)

    TileNameEnum.RABBIT.id should be(1)
    TileNameEnum.CAT.id should be(2)
    TileNameEnum.DOG.id should be(3)
    TileNameEnum.HORSE.id should be(4)
    TileNameEnum.CAMEL.id should be(5)
    TileNameEnum.ELEPHANT.id should be(6)
  }

}
