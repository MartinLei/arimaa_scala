package controller.impl

import controller.impl.messages.Message
import model.impl.PlayerNameEnum
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class EndModeSpec extends FlatSpec with Matchers {
  "changePlayer" should "get end game message" in {
    val endMode = new EndMode()
    endMode.changePlayer should be(Message.endGame)
  }

  "moveTile" should "get end game message" in {
    val endMode = new EndMode()
    endMode.moveTile(new Position(1, 1), new Position(1, 2)) should be(List(Message.endGame.text))
  }

  "moveTileUndo" should "get end game message" in {
    val endMode = new EndMode()
    endMode.moveTileUndo should be(List(Message.endGame.text))
  }
  "getWinnerName" should "get player NONE" in {
    val endMode = new EndMode()
    endMode.getWinnerName should be(PlayerNameEnum.NONE)
  }


}
