package controller.impl.command

import controller.impl.command.impl.MoveCommand
import controller.impl.messages.Message
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class ActionCommandSpec extends FlatSpec with Matchers {
  val field = new Field()
  val commandList = List(
    new MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)),
    new MoveCommand(field, PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3)))

  val actionCommand = new ActionCommand(commandList)

  "doAction" should "execute all commands" in {
    val commandMessageListShould = List(
      Message.doMove(new Position(1, 2), new Position(1, 3)),
      Message.doMove(new Position(2, 2), new Position(2, 3)))

    val commandMessageList = actionCommand.doAction()

    commandMessageList shouldEqual commandMessageListShould
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
  }

  "undoAction" should "execute all undo commands" in {

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)

    val undoMessageListShould = List(
      Message.undoMove(new Position(2, 2), new Position(2, 3)),
      Message.undoMove(new Position(1, 2), new Position(1, 3)))

    val undoMessageList = actionCommand.undoAction()
    undoMessageList shouldEqual undoMessageListShould

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.NONE)
  }
}
