package controller.impl.command

import controller.impl.command.impl.{MoveCommand, PushCommand, TrapCommand}
import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class TurnManagerSpec extends FlatSpec with Matchers {

  "TurnManager" should "have a construct for init it with a turn" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val field = new Field(playerGoldTiles, Set())
    val actionCommand = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))

    val turnManager = new TurnManager(PlayerNameEnum.GOLD)
    turnManager.doAction(actionCommand) should
      be(List(MessageText.doMove(new Position(1, 1), new Position(1, 2))))
  }

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
  it should "empty stack if stack is empty" in {
    val turnManager = new TurnManager
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

    turnManager.getActualPlayerLastActionPosTo should be(Option(null))
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
  it should "false, if stack is empty" in {
    val turnManager = new TurnManager
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

  "isLastActionThirdTimeRepetition" should "true, if the move is the 3th time of repetition in players game" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.DOG, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.DOG, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val actionGold1 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))
    val actionGold2 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 1))))
    val actionGold3 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))
    val actionGold4 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 1))))
    val actionGold5 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))

    val actionSilver1 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.SILVER, new Position(8, 8), new Position(8, 7))))
    val actionSilver2 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.SILVER, new Position(8, 7), new Position(8, 6))))
    val actionSilver3 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.SILVER, new Position(8, 6), new Position(8, 5))))
    val actionSilver4 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.SILVER, new Position(8, 5), new Position(8, 4))))

    val turnManager = new TurnManager()

    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionGold1)
    turnManager.addTurn(PlayerNameEnum.SILVER)
    turnManager.doAction(actionSilver1)
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionGold2)
    turnManager.addTurn(PlayerNameEnum.SILVER)
    turnManager.doAction(actionSilver2)
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionGold3)
    turnManager.addTurn(PlayerNameEnum.SILVER)
    turnManager.doAction(actionSilver3)
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionGold4)
    turnManager.addTurn(PlayerNameEnum.SILVER)
    turnManager.doAction(actionSilver4)
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionGold5)

    turnManager.isLastActionThirdTimeRepetition should be(true)
  }
  it should "false, if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.DOG, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.DOG, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val actionGold1 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))
    val actionGold2 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 1))))
    val actionGold3 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))
    val actionGold4 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 1))))
    val actionGold5 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))
    val actionGold6 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    val actionSilver1 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.SILVER, new Position(8, 8), new Position(8, 7))))
    val actionSilver2 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.SILVER, new Position(8, 7), new Position(8, 6))))
    val actionSilver3 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.SILVER, new Position(8, 6), new Position(8, 5))))
    val actionSilver4 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.SILVER, new Position(8, 5), new Position(8, 4))))

    val turnManager = new TurnManager()

    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionGold1)
    turnManager.addTurn(PlayerNameEnum.SILVER)
    turnManager.doAction(actionSilver1)
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionGold2)
    turnManager.addTurn(PlayerNameEnum.SILVER)
    turnManager.doAction(actionSilver2)
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionGold3)
    turnManager.addTurn(PlayerNameEnum.SILVER)
    turnManager.doAction(actionSilver3)
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionGold4)
    turnManager.addTurn(PlayerNameEnum.SILVER)
    turnManager.doAction(actionSilver4)
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(actionGold5)
    turnManager.doAction(actionGold6)
    turnManager.isLastActionThirdTimeRepetition should be(false)
  }
  it should "false, if stack is empty" in {
    val turnManager = new TurnManager()
    turnManager.isLastActionThirdTimeRepetition should be(false)
  }

}


