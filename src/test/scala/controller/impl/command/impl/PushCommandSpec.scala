package controller.impl.command.impl

import controller.impl.messages.Message
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PushCommandSpec extends FlatSpec with Matchers {

  val field = new Field()
  val pushCommand = PushCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

  "doCommand" should "push the tile to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    pushCommand.doCommand() should be(Message.doPush(new Position(1, 2), new Position(1, 3)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
  }
  "unDolCommand" should "push the tile back to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    pushCommand.undoCommand() should be(Message.undoPush(new Position(1, 2), new Position(1, 3)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
  }
}
