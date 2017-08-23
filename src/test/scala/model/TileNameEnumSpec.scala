package model

import org.scalatest._

class TileNameEnumSpec extends FlatSpec with Matchers {

  "The TileName" should "have this elements with string representation" in {
    TileNameEnum.RABBIT.toString should be("R")
    TileNameEnum.CAT.toString should be("C")
    TileNameEnum.DOG.toString should be("D")
    TileNameEnum.HORSE.toString should be("H")
    TileNameEnum.CAMEL.toString should be("L")
    TileNameEnum.ELEPHANT.toString should be("E")
  }

}
