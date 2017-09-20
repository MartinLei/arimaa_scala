package controller.impl

import controller.ControllerTrait
import model.impl.{PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class ControllerSpec extends FlatSpec with Matchers {


  "A controller" should "give the tile on the given position" in {
    val controller: ControllerTrait = new Controller()
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(1, 1)) should be(TileNameEnum.NONE)
  }


}
