package controller.impl.command.impl

import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class TrapCommandSpec extends FlatSpec with Matchers {

  val playerGoldTiles = Set(new Tile(TileNameEnum.RABBIT, new Position(3, 3)))
  val field = new Field(playerGoldTiles, Set())

  val trapCommand = TrapCommand(field, PlayerNameEnum.GOLD, new Position(3, 3))

  "doCommand" should "remove tile from the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.RABBIT)

    trapCommand.doCommand() should be(MessageText.doTrap(new Position(3, 3)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)
  }
  "undoCommand" should "respawn the tile back to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)

    trapCommand.undoCommand() should be(MessageText.undoTrap(new Position(3, 3)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.RABBIT)
  }

  "equals" should "true, if name and pos are the same" in {
    val field1 = new Field()
    val trapCommand1 = TrapCommand(field1, PlayerNameEnum.GOLD, new Position(1, 2))

    val field2 = new Field()
    val trapCommand2 = TrapCommand(field2, PlayerNameEnum.GOLD, new Position(1, 2))

    trapCommand1 should be(trapCommand2)
  }
  it should "false if not" in {
    val field1 = new Field()
    val trapCommand1 = TrapCommand(field1, PlayerNameEnum.GOLD, new Position(1, 2))

    val field2 = new Field()
    val trapCommand2 = TrapCommand(field2, PlayerNameEnum.GOLD, new Position(1, 3))

    trapCommand1 should not be trapCommand2
  }
}
