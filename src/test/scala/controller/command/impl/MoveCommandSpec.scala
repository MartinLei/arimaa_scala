package controller.command.impl

import controller.impl.command.impl.MoveCommand
import controller.impl.messages.impl.UndoMoveMessage
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class MoveCommandSpec extends FlatSpec with Matchers {

  val field = new Field()
  val moveCommand = new MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

  "doCommand" should "move the tile to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    moveCommand.doCommand()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
  }
  "unDolCommand" should "move the tile back to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    moveCommand.undoCommand() should be(new UndoMoveMessage(new Position(1, 3), new Position(1, 2)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
  }
}
