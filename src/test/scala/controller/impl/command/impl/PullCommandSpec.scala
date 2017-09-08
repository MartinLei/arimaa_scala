package controller.impl.command.impl

import controller.impl.messages.impl.Message
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PullCommandSpec extends FlatSpec with Matchers {

  val field = new Field()
  val pullCommand = new PullCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

  "doCommand" should "pull the tile to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    pullCommand.doCommand() should be(Message.doPull(new Position(1, 2), new Position(1, 3)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
  }
  "unDolCommand" should "pull the tile back to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    pullCommand.undoCommand() should be(Message.undoPull(new Position(1, 2), new Position(1, 3)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
  }
}
