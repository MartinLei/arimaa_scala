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
}
