package controller.impl.command

import controller.impl.command.impl.{MoveCommand, PushCommand, TrapCommand}
import controller.impl.messages.Message
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class UndoActionManagerSpec extends FlatSpec with Matchers {
  val field = new Field()

  val commandList1 = List(
    MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)),
    MoveCommand(field, PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3)))
  val actionCommand1 = new ActionCommand(commandList1)

  val commandList2 = List(
    MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4)),
    MoveCommand(field, PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 4)))
  val actionCommand2 = new ActionCommand(commandList2)

  val undoActionManager = new UndoActionManager()

  "doAction" should "do the action" in {
    val doMessageListShould1 = List(
      Message.doMove(new Position(1, 2), new Position(1, 3)),
      Message.doMove(new Position(2, 2), new Position(2, 3)))
    val doMessageList1 = undoActionManager.doAction(actionCommand1)

    doMessageList1 shouldEqual doMessageListShould1
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)

    val doMessageListShould2 = List(
      Message.doMove(new Position(1, 3), new Position(1, 4)),
      Message.doMove(new Position(2, 3), new Position(2, 4)))
    val doMessageList2 = undoActionManager.doAction(actionCommand2)

    doMessageList2 shouldEqual doMessageListShould2
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 4)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.HORSE)
  }

  "undoAction" should "undo the action" in {
    val undoMessageListShould1 = List(
      Message.undoMove(new Position(2, 3), new Position(2, 4)),
      Message.undoMove(new Position(1, 3), new Position(1, 4)))

    val undoMessageList1 = undoActionManager.undoAction()
    undoMessageList1 shouldEqual undoMessageListShould1
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 4)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.NONE)

    val undoMessageListShould2 = List(
      Message.undoMove(new Position(2, 2), new Position(2, 3)),
      Message.undoMove(new Position(1, 2), new Position(1, 3)))

    val undoMessageList2 = undoActionManager.undoAction()

    undoMessageList2 shouldEqual undoMessageListShould2
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.NONE)

    undoActionManager.undoAction() shouldEqual List(Message.emptyStack)

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.NONE)
  }

  "getLastActionPushCommandPosFrom" should "give the last actions first command" in {
    val field1 = new Field()
    val undoActionManager1 = new UndoActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field1, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(PushCommand(field1, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    undoActionManager1.doAction(actionCommand1)
    undoActionManager1.doAction(actionCommand2)

    undoActionManager1.getLastActionPushCommandPosFrom should be(Some(new Position(1, 2)))
  }
  it should "null if no  push command action" in {
    val field1 = new Field()
    val undoActionManager1 = new UndoActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field1, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(commandList2)

    undoActionManager1.doAction(actionCommand1)
    undoActionManager1.doAction(actionCommand2)

    undoActionManager1.getLastActionPushCommandPosFrom should be(Option(null))
  }
  it should "null if empty" in {
    val field1 = new Field()
    val undoActionManager1 = new UndoActionManager()
    undoActionManager1.getLastActionPushCommandPosFrom should be(Option(null))
  }

  "getLastActionCommandPosFrom" should "give the last actions first push command" in {
    val field1 = new Field()
    val undoActionManager1 = new UndoActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field1, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(PushCommand(field1, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4))))

    undoActionManager1.doAction(actionCommand1)
    undoActionManager1.doAction(actionCommand2)

    undoActionManager1.getLastActionCommandPosFrom should be(Some(new Position(1, 3)))
  }
  it should "give the last actions first move command" in {
    val field1 = new Field()
    val undoActionManager1 = new UndoActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field1, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(MoveCommand(field1, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4))))

    undoActionManager1.doAction(actionCommand1)
    undoActionManager1.doAction(actionCommand2)

    undoActionManager1.getLastActionCommandPosFrom should be(Some(new Position(1, 3)))
  }
  it should "null if no push or move command action" in {
    val field1 = new Field()
    val undoActionManager1 = new UndoActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field1, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(TrapCommand(field1, PlayerNameEnum.GOLD, new Position(1, 2))))

    undoActionManager1.doAction(actionCommand1)
    undoActionManager1.doAction(actionCommand2)

    undoActionManager1.getLastActionCommandPosFrom should be(Option(null))
  }
  it should "null if empty" in {
    val field1 = new Field()
    val undoActionManager1 = new UndoActionManager()
    undoActionManager1.getLastActionCommandPosFrom should be(Option(null))
  }

  "getLastActionCommandPosTo" should "give the last actions first push command posTo" in {
    val field1 = new Field()
    val undoActionManager1 = new UndoActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field1, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(PushCommand(field1, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4))))

    undoActionManager1.doAction(actionCommand1)
    undoActionManager1.doAction(actionCommand2)

    undoActionManager1.getLastActionCommandPosTo should be(Some(new Position(1, 4)))
  }
  it should "give the last actions first move command" in {
    val field1 = new Field()
    val undoActionManager1 = new UndoActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field1, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(MoveCommand(field1, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4))))

    undoActionManager1.doAction(actionCommand1)
    undoActionManager1.doAction(actionCommand2)

    undoActionManager1.getLastActionCommandPosTo should be(Some(new Position(1, 4)))
  }
  it should "null if no push or move command action" in {
    val field1 = new Field()
    val undoActionManager1 = new UndoActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field1, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(TrapCommand(field1, PlayerNameEnum.GOLD, new Position(1, 2))))

    undoActionManager1.doAction(actionCommand1)
    undoActionManager1.doAction(actionCommand2)

    undoActionManager1.getLastActionCommandPosTo should be(Option(null))
  }
  it should "null if empty" in {
    val undoActionManager1 = new UndoActionManager()
    undoActionManager1.getLastActionCommandPosTo should be(Option(null))
  }
}


