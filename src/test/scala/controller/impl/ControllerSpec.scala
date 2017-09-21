package controller.impl

import controller.impl.messages.{Message, MessageText}
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
  it should "set Mode to endMode if message ist win" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val controller = new Controller
    controller.setMode(ModeEnum.GAME, field)

    controller.moveTile(new Position(1, 7), new Position(1, 8))

    controller.changePlayer should be(MessageText.doWin(PlayerNameEnum.GOLD))

    controller.getMode should be(ModeEnum.END)
    controller.moveTile(new Position(1, 8), new Position(2, 8)) should be(Message.endGame)
  }


}
