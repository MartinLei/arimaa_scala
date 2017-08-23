package model

import org.scalatest.{FlatSpec, Matchers}

class PlayerSpec extends FlatSpec with Matchers {

  "A Player" should "have a name" in {
    val player: Player = new Player(PlayerNameEnum.GOLD)
    player.name should be(PlayerNameEnum.GOLD)
  }

}
