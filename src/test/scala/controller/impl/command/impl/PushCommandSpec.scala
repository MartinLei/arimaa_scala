package controller.impl.command.impl

import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PushCommandSpec extends FlatSpec with Matchers {

  val fieldGlobal = new Field()
  val pushCommandGlobal = PushCommand(fieldGlobal, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

  "doCommand" should "push the tile to the given position" in {
    fieldGlobal.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    fieldGlobal.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    pushCommandGlobal.doCommand() should be(MessageText.doPush(new Position(1, 2), new Position(1, 3)))

    fieldGlobal.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    fieldGlobal.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
  }
  it should "get error if change tile is not possible" in {
    val field = new Field()
    val pushCommand = PushCommand(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(5, 6))
    pushCommand.doCommand() should
      be(MessageText.errorChangeTile(PlayerNameEnum.GOLD, new Position(5, 5), new Position(5, 6)))
  }
  "undoCommand" should "push the tile back to the given position" in {
    fieldGlobal.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    fieldGlobal.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    pushCommandGlobal.undoCommand() should be(MessageText.undoPush(new Position(1, 2), new Position(1, 3)))

    fieldGlobal.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    fieldGlobal.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
  }

  "equals" should "true, if name and pos are the same" in {
    val field1 = new Field()
    val pushCommand1 = PushCommand(field1, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

    val field2 = new Field()
    val pushCommand2 = PushCommand(field2, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

    pushCommand1 should be(pushCommand2)
  }
  it should "false if not" in {
    val field1 = new Field()
    val pushCommand1 = PushCommand(field1, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))

    val field2 = new Field()
    val pushCommand2 = PushCommand(field2, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4))

    pushCommand1 should not be pushCommand2
  }
}
