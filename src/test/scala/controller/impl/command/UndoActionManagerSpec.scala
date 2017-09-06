package controller.impl.command

import controller.impl.command.impl.MoveCommand
import controller.impl.messages.impl.{EmptyUndoStackMessage, UndoMoveMessage}
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class UndoActionManagerSpec extends FlatSpec with Matchers {
  val field = new Field()

  val preCommand1 = new MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))
  val posCommand1 = new MoveCommand(field, PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
  val actionCommand1 = new ActionCommand(preCommand1, posCommand1)

  val preCommand2 = new MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4))
  val posCommand2 = new MoveCommand(field, PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 4))
  val actionCommand2 = new ActionCommand(preCommand2, posCommand2)

  val undoActionManager = new UndoActionManager()

  "doAction" should "do the action" in {
    undoActionManager.doAction(actionCommand1)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)

    undoActionManager.doAction(actionCommand2)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 4)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.HORSE)
  }

  "undoAction" should "undo the action" in {
    val undoMessageListShould1 = List(
      new UndoMoveMessage(new Position(1, 3), new Position(1, 2)),
      new UndoMoveMessage(new Position(2, 3), new Position(2, 2)))

    val undoMessageList1 = undoActionManager.undoAction()
    undoMessageList1 shouldEqual undoMessageListShould1
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.NONE)

    val undoMessageListShould2 = List(
      new UndoMoveMessage(new Position(1, 4), new Position(1, 3)),
      new UndoMoveMessage(new Position(2, 4), new Position(2, 3)))

    val undoMessageList2 = undoActionManager.undoAction()
    undoMessageList2 shouldEqual undoMessageListShould2
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 4)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.NONE)

    undoActionManager.undoAction() shouldEqual List(new EmptyUndoStackMessage)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 4)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.NONE)
  }

}


