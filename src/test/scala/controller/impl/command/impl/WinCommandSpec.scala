package controller.impl.command.impl

import controller.impl.messages.Message
import model.impl.{Field, PlayerNameEnum}
import org.scalatest.{FlatSpec, Matchers}

class WinCommandSpec extends FlatSpec with Matchers {

  val field = new Field()
  val winCommand = WinCommand(field, PlayerNameEnum.GOLD)

  "doCommand" should "say the winner" in {
    field.winPlayerName should be(PlayerNameEnum.NONE)
    winCommand.doCommand() should be(Message.doWin(PlayerNameEnum.GOLD))
    field.winPlayerName should be(PlayerNameEnum.GOLD)
  }
  "undoCommand" should "move the tile back to the given position" in {
    field.winPlayerName should be(PlayerNameEnum.GOLD)
    winCommand.undoCommand() should be(Message.undoWin(PlayerNameEnum.GOLD))
    field.winPlayerName should be(PlayerNameEnum.NONE)
  }
}
