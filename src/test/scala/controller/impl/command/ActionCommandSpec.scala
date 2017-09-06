package controller.impl.command

import controller.impl.command.impl.MoveCommand
import controller.impl.messages.impl.{MoveMessage, UndoMoveMessage}
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
      new MoveMessage(new Position(1, 2), new Position(1, 3)),
      new MoveMessage(new Position(2, 2), new Position(2, 3)))

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
      new UndoMoveMessage(new Position(1, 3), new Position(1, 2)),
      new UndoMoveMessage(new Position(2, 3), new Position(2, 2)))

    val undoMessageList = actionCommand.undoAction()
    undoMessageList shouldEqual undoMessageListShould

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.NONE)
  }


  /*
  it should "do both doCommands" in{
    val field = new Field()
    val moveCommand1 = new MoveCommand(field,PlayerNameEnum.GOLD, new Position(2,2), new Position(2,3))
    val moveCommand2 = new MoveCommand(field,PlayerNameEnum.GOLD, new Position(2,3), new Position(3,3))
    moveCommand1.doCommand()
    moveCommand2.doCommand()
    field.getTileName(PlayerNameEnum.GOLD,new Position(2,3)) should be (TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD,new Position(3,3)) should be (TileNameEnum.CAT)


    val moveCommand3 = new MoveCommand(field,PlayerNameEnum.GOLD, new Position(2,3), new Position(2,4))
    val trapCommand = new RemoveCommand(field,PlayerNameEnum.GOLD,new Position(3,3),new Position(3,3))
    val ac = new ActionCommand(moveCommand3,trapCommand)

    ac.doCommand()
    field.getTileName(PlayerNameEnum.GOLD,new Position(2,4)) should be (TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD,new Position(3,3)) should be (TileNameEnum.NONE)
  }
  */

}
