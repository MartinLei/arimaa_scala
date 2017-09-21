package controller.impl.mode

import controller.impl.messages.Message
import model.impl.{Field, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class EndModeSpec extends FlatSpec with Matchers {
  val playerGoldTiles = Set(
    new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
  val playerSilverTiles = Set(
    new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
  val fieldGlobal = new Field(playerGoldTiles, playerSilverTiles)

  "A End Mode" should "have a mode of END" in {
    val endMode = new EndMode(fieldGlobal)
    endMode.modeType should be(ModeEnum.END)

    val fieldString: String = "\n" +
      "  +-----------------+\n" +
      "8 | r               |\n" +
      "7 |                 |\n" +
      "6 |     X     X     |\n" +
      "5 |                 |\n" +
      "4 |                 |\n" +
      "3 |     X     X     |\n" +
      "2 |                 |\n" +
      "1 | R               |\n" +
      "  +-----------------+\n" +
      "    a b c d e f g h  \n"

    endMode.getFieldAsString should be(fieldString)
  }
  "changePlayer" should "get end game message" in {
    val endMode = new EndMode(fieldGlobal)
    endMode.changePlayer should be(Message.endGame)
  }

  "moveTile" should "get end game message" in {
    val endMode = new EndMode(fieldGlobal)
    endMode.moveTile(new Position(1, 1), new Position(1, 2)) should be(List(Message.endGame.text))
  }

  "moveTileUndo" should "get end game message" in {
    val endMode = new EndMode(fieldGlobal)
    endMode.moveTileUndo should be(List(Message.endGame.text))
  }

}
