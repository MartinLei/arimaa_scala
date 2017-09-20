package controller.impl.command.impl

import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PullCommandSpec extends FlatSpec with Matchers {

  val field = new Field()
  val pullCommand = PullCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

  "doCommand" should "pull the tile to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    pullCommand.doCommand() should be(MessageText.doPull(new Position(1, 2), new Position(1, 3)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
  }
  "undoCommand" should "pull the tile back to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    pullCommand.undoCommand() should be(MessageText.undoPull(new Position(1, 2), new Position(1, 3)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
  }

  "equals" should "true, if name and pos are the same" in {
    val field1 = new Field()
    val pullCommand1 = PullCommand(field1, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

    val field2 = new Field()
    val pullCommand2 = PullCommand(field2, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

    pullCommand1 should be(pullCommand2)
  }
  it should "false if not" in {
    val field1 = new Field()
    val pullCommand1 = PullCommand(field1, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

    val field2 = new Field()
    val pullCommand2 = PullCommand(field2, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4))

    pullCommand1 should not be pullCommand2
  }
}
