package controller.impl.command.impl

import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class MoveCommandSpec extends FlatSpec with Matchers {

  val field = new Field()
  val moveCommand = MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

  "doCommand" should "move the tile to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    moveCommand.doCommand() should be(MessageText.doMove(new Position(1, 2), new Position(1, 3)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
  }
  it should "get error if change tile is not possible" in {
    val moveCommand = MoveCommand(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(5, 6))
    moveCommand.doCommand() should
      be(MessageText.errorChangeTile(PlayerNameEnum.GOLD, new Position(5, 5), new Position(5, 6)))
  }
  "undoCommand" should "move the tile back to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    moveCommand.undoCommand() should be(MessageText.undoMove(new Position(1, 2), new Position(1, 3)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
  }

  "equals" should "true, if name and pos are the same" in {
    val field1 = new Field()
    val moveCommand1 = MoveCommand(field1, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

    val field2 = new Field()
    val moveCommand2 = MoveCommand(field2, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

    moveCommand1 should be(moveCommand2)
  }
  it should "false if not" in {
    val field1 = new Field()
    val moveCommand1 = MoveCommand(field1, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

    val field2 = new Field()
    val moveCommand2 = MoveCommand(field2, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4))

    moveCommand1 should not be moveCommand2
  }
}
