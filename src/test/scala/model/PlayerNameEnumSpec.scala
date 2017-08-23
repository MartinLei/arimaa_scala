package model

import org.scalatest._

class PlayerNameEnumSpec extends FlatSpec with Matchers {

  "The PlayerNameEnum" should "have this elements" in {
    PlayerNameEnum.GOLD.toString should be("G")
    PlayerNameEnum.SILVER.toString should be("S")
  }
}
