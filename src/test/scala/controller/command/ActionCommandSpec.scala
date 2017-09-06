package controller.command

import controller.impl.command.ActionCommand
import controller.impl.command.impl.{MoveCommand, RemoveCommand}
import controller.impl.messages.impl.UndoMoveMessage
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class ActionCommandSpec extends FlatSpec with Matchers {


  "ActionCommand" should "have a constructor for a pre and post command" in {
    val field = new Field()
    val moveCommand = new MoveCommand(field, PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 4))
    val trapCommand = new RemoveCommand(field, PlayerNameEnum.GOLD, new Position(3, 3), new Position(3, 3))
    val actionCommand = new ActionCommand(moveCommand, trapCommand)
  }
  /*
  it should "have a constructor for only one command" in {
    val field = new Field()
    val moveCommand = new MoveCommand(field,PlayerNameEnum.GOLD, new Position(2,3), new Position(2,4))
    val ac = new ActionCommand(moveCommand)
  }
  */
  it should "do both doCommands" in {
    val field = new Field()
    val moveCommand1 = new MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))
    val moveCommand2 = new MoveCommand(field, PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))

    val actionCommand = new ActionCommand(moveCommand1, moveCommand2)
    actionCommand.doAction()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
  }
  it should "do both undoCommands" in {
    val field = new Field()
    val moveCommand1 = new MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))
    val moveCommand2 = new MoveCommand(field, PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))

    val actionCommand = new ActionCommand(moveCommand1, moveCommand2)
    actionCommand.doAction()

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
