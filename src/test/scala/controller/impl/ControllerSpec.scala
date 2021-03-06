package controller.impl

import controller.impl.messages.MessageText
import controller.impl.mode.ModeEnum
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

  "getFieldAsString" should "get the actual filed as string" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val controller = new Controller()
    controller.setMode(ModeEnum.GAME, field)

    controller.moveTile(new Position(1, 1), new Position(1, 2))

    val fieldString: String = "\n" +
      "  +-----------------+\n" +
      "8 |               r |\n" +
      "7 |                 |\n" +
      "6 |     X     X     |\n" +
      "5 |                 |\n" +
      "4 |                 |\n" +
      "3 |     X     X     |\n" +
      "2 | R               |\n" +
      "1 |                 |\n" +
      "  +-----------------+\n" +
      "    a b c d e f g h  \n"

    controller.getFieldAsString should be(fieldString)
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

    controller.changePlayer should
      be(MessageText.changePlayer(PlayerNameEnum.SILVER))
  }
  it should "set Mode to endMode if player win" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val controller = new Controller
    controller.setMode(ModeEnum.GAME, field)

    controller.moveTile(new Position(1, 7), new Position(1, 8))

    val fieldAsStringGameMode = controller.getFieldAsString

    controller.changePlayer should be(MessageText.doWin(PlayerNameEnum.GOLD))

    controller.getMode should be(ModeEnum.END)
    controller.moveTile(new Position(1, 8), new Position(2, 8)) should be(List(MessageText.endGame))

    controller.getFieldAsString should be(fieldAsStringGameMode)
  }


}
