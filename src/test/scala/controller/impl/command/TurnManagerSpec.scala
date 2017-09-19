package controller.impl.command

import controller.impl.command.impl.{MoveCommand, PushCommand, TrapCommand}
import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class TurnManagerSpec extends FlatSpec with Matchers {

  "addTurn" should "add it to stack" in {
    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD) should be(MessageText.changePlayer(PlayerNameEnum.GOLD))
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
  it should "be next player if actual player stack is empty" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val actionCommand1 = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))
    val actionCommand2 = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.SILVER, new Position(8, 8), new Position(8, 7))))

    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)

    turnManager.doAction(actionCommand1) should
      be(List(MessageText.doMove(new Position(1, 1), new Position(1, 2))))

    turnManager.addTurn(PlayerNameEnum.SILVER)

    turnManager.doAction(actionCommand2) should
      be(List(MessageText.doMove(new Position(8, 8), new Position(8, 7))))

    turnManager.undoAction should
      be(List(MessageText.undoMove(new Position(8, 8), new Position(8, 7))))

    turnManager.undoAction should
      be(List(MessageText.changePlayer(PlayerNameEnum.GOLD)))

    turnManager.undoAction() should
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

    val actionCommand = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionCommand)

    turnManager.isLastAPushCommand should be(false)
  }

  "isTurnEmpty" should "true if player has no actions" in {
    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)

    turnManager.isTurnEmpty should be(true)
  }
  it should "true if stack empty" in {
    val turnManager = new TurnManager

    turnManager.isTurnEmpty should be(true)
  }
  it should "false if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())

    val actionCommand = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionCommand)

    turnManager.isTurnEmpty should be(false)
  }

}


