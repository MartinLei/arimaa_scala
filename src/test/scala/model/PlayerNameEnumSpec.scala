package model

import model.impl.PlayerNameEnum
import org.scalatest._

class PlayerNameEnumSpec extends FlatSpec with Matchers {

  "The PlayerNameEnum" should "have this elements" in {
    PlayerNameEnum.GOLD.toString should be("G")
    PlayerNameEnum.SILVER.toString should be("S")
  }

  "getInvertPlayer" should "invert the given player" in {
    PlayerNameEnum.getInvertPlayer(PlayerNameEnum.GOLD) should be(PlayerNameEnum.SILVER)
    PlayerNameEnum.getInvertPlayer(PlayerNameEnum.SILVER) should be(PlayerNameEnum.GOLD)
  }
}
