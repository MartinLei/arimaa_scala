package controller.impl.command.impl

import controller.impl.messages.Message
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class RemoveCommandSpec extends FlatSpec with Matchers {

  val field = new Field()
  val removeCommand = new RemoveCommand(field, PlayerNameEnum.GOLD, new Position(3, 2))

  "doCommand" should "remove tile from the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.CAT)

    removeCommand.doCommand() should be(Message.doTrap(new Position(3, 2)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.NONE)
  }
  "unDoCommand" should "respawn the tile back to the given position" in {
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.NONE)

    removeCommand.undoCommand() should be(Message.undoTrap(new Position(3, 2)))

    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.CAT)
  }
}
