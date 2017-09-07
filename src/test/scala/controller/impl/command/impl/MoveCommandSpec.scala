package controller.impl.command.impl

import controller.impl.messages.impl.Message
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class MoveCommandSpec extends FlatSpec with Matchers {

  val field = new Field()
  val moveCommand = new MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

  "doCommand" should "move the tile to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    moveCommand.doCommand() should be(Message.doMove(new Position(1, 2), new Position(1, 3)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
  }
  "unDolCommand" should "move the tile back to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    moveCommand.undoCommand() should be(Message.undoMove(new Position(1, 2), new Position(1, 3)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
  }
}
