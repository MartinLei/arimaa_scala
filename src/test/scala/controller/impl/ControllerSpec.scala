package controller.impl

import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class ControllerSpec extends FlatSpec with Matchers {

  "setMode" should "set mode" in {
    val controller = new Controller()
    controller.getMode should be(ModeEnum.GAME)
    controller.setMode(ModeEnum.GAME, new Field())
    controller.getMode should be(ModeEnum.GAME)
  }

  "changePlayer" should "change player" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val controller = new Controller()
    controller.setMode(ModeEnum.GAME, field)

    controller.moveTile(new Position(1, 1), new Position(1, 2))

    controller.changePlayer() should
      be(List(
        MessageText.changePlayer(PlayerNameEnum.SILVER)))
  }


}
