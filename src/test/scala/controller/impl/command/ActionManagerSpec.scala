package controller.impl.command

import controller.impl.command.impl.{MoveCommand, PushCommand, TrapCommand}
import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class ActionManagerSpec extends FlatSpec with Matchers {
  val playerGoldTiles = Set(
    new Tile(TileNameEnum.RABBIT, new Position(1, 2)),
    new Tile(TileNameEnum.HORSE, new Position(2, 2)))
  val globalField = new Field(playerGoldTiles, Set())

  val commandGlobal1 = List(
    MoveCommand(globalField, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)),
    MoveCommand(globalField, PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3)))
  val actionGlobal1 = new ActionCommand(commandGlobal1)

  val commandGlobal2 = List(
    MoveCommand(globalField, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4)),
    MoveCommand(globalField, PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 4)))
  val actionGlobal2 = new ActionCommand(commandGlobal2)

  val globalActionManager = new ActionManager()

  "doAction" should "do the action" in {
    val doMessageListShould1 = List(
      MessageText.doMove(new Position(1, 2), new Position(1, 3)),
      MessageText.doMove(new Position(2, 2), new Position(2, 3)))
    val doMessageList1 = globalActionManager.doAction(actionGlobal1)

    doMessageList1 shouldEqual doMessageListShould1
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.NONE)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)

    val doMessageListShould2 = List(
      MessageText.doMove(new Position(1, 3), new Position(1, 4)),
      MessageText.doMove(new Position(2, 3), new Position(2, 4)))
    val doMessageList2 = globalActionManager.doAction(actionGlobal2)

    doMessageList2 shouldEqual doMessageListShould2
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.NONE)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(1, 4)) should be(TileNameEnum.RABBIT)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.HORSE)
  }

  "undoAction" should "undo the action" in {
    val undoMessageListShould1 = List(
      MessageText.undoMove(new Position(2, 3), new Position(2, 4)),
      MessageText.undoMove(new Position(1, 3), new Position(1, 4)))

    val undoMessageList1 = globalActionManager.undoAction()
    undoMessageList1 shouldEqual undoMessageListShould1
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(1, 4)) should be(TileNameEnum.NONE)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.NONE)

    val undoMessageListShould2 = List(
      MessageText.undoMove(new Position(2, 2), new Position(2, 3)),
      MessageText.undoMove(new Position(1, 2), new Position(1, 3)))

    val undoMessageList2 = globalActionManager.undoAction()

    undoMessageList2 shouldEqual undoMessageListShould2
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.NONE)

    globalActionManager.undoAction() shouldEqual List(MessageText.emptyStack)

    globalField.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    globalField.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.NONE)
  }
  "getLastActionPushCommandPosFrom" should "give the last actions first command" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())

    val actionManager = new ActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    actionManager.doAction(actionCommand1)
    actionManager.doAction(actionCommand2)

    actionManager.getLastActionPushCommandPosFrom should be(Some(new Position(1, 2)))
  }
  it should "null if no  push command action" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())
    val actionManager = new ActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    actionManager.doAction(actionCommand1)
    actionManager.doAction(actionCommand2)

    actionManager.getLastActionPushCommandPosFrom should be(Option(null))
  }
  it should "null if empty" in {
    val actionManager = new ActionManager()
    actionManager.getLastActionPushCommandPosFrom should be(Option(null))
  }
  "getLastActionCommandPosFrom" should "give the last actions first push command" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())
    val actionManager = new ActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    actionManager.doAction(actionCommand1)
    actionManager.doAction(actionCommand2)

    actionManager.getLastActionCommandPosFrom should be(Some(new Position(1, 2)))
  }
  it should "give the last actions first move command" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())
    val actionManager = new ActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    actionManager.doAction(actionCommand1)
    actionManager.doAction(actionCommand2)

    actionManager.getLastActionCommandPosFrom should be(Some(new Position(1, 2)))
  }
  it should "null if no push or move command action" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())
    val actionManager = new ActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(TrapCommand(field, PlayerNameEnum.GOLD, new Position(1, 2))))

    actionManager.doAction(actionCommand1)
    actionManager.doAction(actionCommand2)

    actionManager.getLastActionCommandPosFrom should be(Option(null))
  }
  it should "null if empty" in {
    val actionManager = new ActionManager()
    actionManager.getLastActionCommandPosFrom should be(Option(null))
  }

  "getLastActionCommandPosTo" should "give the last actions first push command posTo" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())
    val actionManager = new ActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    actionManager.doAction(actionCommand1)
    actionManager.doAction(actionCommand2)

    actionManager.getLastActionCommandPosTo should be(Some(new Position(1, 3)))
  }
  it should "give the last actions first move command" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())
    val actionManager = new ActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    actionManager.doAction(actionCommand1)
    actionManager.doAction(actionCommand2)

    actionManager.getLastActionCommandPosTo should be(Some(new Position(1, 3)))
  }
  it should "null if no push or move command action" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())
    val actionManager = new ActionManager()
    val actionCommand1 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(8, 2), new Position(8, 3))))
    val actionCommand2 = new ActionCommand(List(TrapCommand(field, PlayerNameEnum.GOLD, new Position(1, 2))))

    actionManager.doAction(actionCommand1)
    actionManager.doAction(actionCommand2)

    actionManager.getLastActionCommandPosTo should be(Option(null))
  }
  it should "null if empty" in {
    val actionManager = new ActionManager()
    actionManager.getLastActionCommandPosTo should be(Option(null))
  }

  "isLastAPushCommand" should "true if last command is a push command" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())
    val actionManager = new ActionManager()
    val actionCommand = new ActionCommand(List(
      PushCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    actionManager.doAction(actionCommand)

    actionManager.isLastAPushCommand should be(true)
  }
  it should "false, if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())
    val actionManager = new ActionManager()
    val actionCommand = new ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    actionManager.doAction(actionCommand)

    actionManager.isLastAPushCommand should be(false)
  }
  it should "false, if stack is empty" in {
    val actionManager = new ActionManager()
    val actionCommand = new ActionCommand(List())

    actionManager.doAction(actionCommand)

    actionManager.isLastAPushCommand should be(false)
  }
}


