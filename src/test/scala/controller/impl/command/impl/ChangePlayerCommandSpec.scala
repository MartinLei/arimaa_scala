package controller.impl.command.impl

import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum}
import org.scalatest.{FlatSpec, Matchers}

class ChangePlayerCommandSpec extends FlatSpec with Matchers {

  val field = new Field()
  val changePlayerCommand = ChangePlayerCommand(field)

  "doCommand" should "change player from gold to silver" in {
    field.actualPlayerName should be(PlayerNameEnum.GOLD)

    changePlayerCommand.doCommand() should be(MessageText.changePlayer(PlayerNameEnum.SILVER))

    field.actualPlayerName should be(PlayerNameEnum.SILVER)

    changePlayerCommand.doCommand() should be(MessageText.changePlayer(PlayerNameEnum.GOLD))

    field.actualPlayerName should be(PlayerNameEnum.GOLD)
  }
  "undoCommand" should "move the tile back to the given position" in {
    field.actualPlayerName should be(PlayerNameEnum.GOLD)

    changePlayerCommand.undoCommand() should be(MessageText.changePlayer(PlayerNameEnum.SILVER))

    field.actualPlayerName should be(PlayerNameEnum.SILVER)

    changePlayerCommand.undoCommand() should be(MessageText.changePlayer(PlayerNameEnum.GOLD))

    field.actualPlayerName should be(PlayerNameEnum.GOLD)
  }
}
