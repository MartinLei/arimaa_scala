package controller.impl.command

import controller.impl.command.impl.{MoveCommand, PushCommand, TrapCommand}
import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class TurnManagerSpec extends FlatSpec with Matchers {

  "addTurn" should "add it to stack" in {
    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)
  }

  "doAction" should "add it to last turn and execute action" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val field = new Field(playerGoldTiles, Set())
    val actionCommand = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))

    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)

    turnManager.doAction(actionCommand) should
      be(List(MessageText.doMove(new Position(1, 1), new Position(1, 2))))
  }
  "undoAction" should "undo the action" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val field = new Field(playerGoldTiles, Set())
    val actionCommand = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))

    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)

    turnManager.doAction(actionCommand) should
      be(List(MessageText.doMove(new Position(1, 1), new Position(1, 2))))
    turnManager.undoAction should
      be(List(MessageText.undoMove(new Position(1, 1), new Position(1, 2))))
    turnManager.undoAction should
      be(List(MessageText.emptyStack))
  }
  "getActualPlayerLastActionPosFrom" should "get the players last action pos From" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(2, 1)))
    val field = new Field(playerGoldTiles, Set())
    val actionCommand = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2)),
      TrapCommand(field, PlayerNameEnum.GOLD, new Position(2, 1))))

    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)

    turnManager.doAction(actionCommand) should
      be(List(
        MessageText.doMove(new Position(1, 1), new Position(1, 2)),
        MessageText.doTrap(new Position(2, 1))))
    turnManager.getActualPlayerLastActionPosFrom should be(Some(new Position(1, 1)))
  }
  it should "null if stack is empty" in {
    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)

    turnManager.getActualPlayerLastActionPosFrom should be(Option(null))
  }
  "getActualPlayerLastActionPosTo" should "get the players last action pos To" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(2, 1)))
    val field = new Field(playerGoldTiles, Set())
    val actionCommand = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2)),
      TrapCommand(field, PlayerNameEnum.GOLD, new Position(2, 1))))

    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)

    turnManager.doAction(actionCommand) should
      be(List(
        MessageText.doMove(new Position(1, 1), new Position(1, 2)),
        MessageText.doTrap(new Position(2, 1))))
    turnManager.getActualPlayerLastActionPosTo should be(Some(new Position(1, 2)))
  }
  it should "null if stack is empty" in {
    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)

    turnManager.getActualPlayerLastActionPosFrom should be(Option(null))
  }
  "isLastAPushCommand" should "true if last command is a push command" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())
    val actionCommand = ActionCommand(List(
      PushCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionCommand)

    turnManager.isLastAPushCommand should be(true)
  }
  it should "false, if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())
    val actionManager = new ActionManager()
    val actionCommand = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))


    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionCommand)

    turnManager.isLastAPushCommand should be(false)
  }
}


