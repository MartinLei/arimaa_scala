package controller.impl.rule

import controller.impl.command.impl.{MoveCommand, PushCommand}
import controller.impl.command.{ActionCommand, TurnManager}
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PreChangePlayerConditionSpec extends FlatSpec with Matchers {

  "isPushNotFinish" should "true if, last command is a push command" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val action = ActionCommand(List(PushCommand(field, PlayerNameEnum.SILVER, new Position(5, 5), new Position(6, 5))))

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(action)

    PreChangePlayerCondition.isPushNotFinish(turnManager) should be(true)
  }
  it should "false, if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val action1 = ActionCommand(List(PushCommand(field, PlayerNameEnum.SILVER, new Position(5, 5), new Position(6, 5))))
    val action2 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(5, 4), new Position(5, 5))))

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(action1)
    turnManager.doAction(action2)

    PreChangePlayerCondition.isPushNotFinish(turnManager) should be(false)
  }
  "isNoTileMovedFromPlayer" should "true, if player no move any tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)

    PreChangePlayerCondition.isNoTileMovedFromPlayer(turnManager) should be(true)
  }
  it should "true,if stack is empty" in {
    val turnManager = new TurnManager()

    PreChangePlayerCondition.isNoTileMovedFromPlayer(turnManager) should be(true)
  }
  it should "false, if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())

    val action = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(action)

    PreChangePlayerCondition.isNoTileMovedFromPlayer(turnManager) should be(false)
  }

  "isMoveThirdTimeRepetition" should "true, if the move is the 3th time of repetition in players game" in {
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

    PreChangePlayerCondition.isMoveThirdTimeRepetition(turnManager) should be(true)
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
    PreChangePlayerCondition.isMoveThirdTimeRepetition(turnManager) should be(false)
  }
}
