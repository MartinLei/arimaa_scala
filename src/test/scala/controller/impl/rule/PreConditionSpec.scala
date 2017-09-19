package controller.impl.rule

import controller.impl.command.impl.{MoveCommand, PushCommand}
import controller.impl.command.{ActionCommand, TurnManager}
import controller.impl.messages.Message
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PreConditionSpec extends FlatSpec with Matchers {

  "precondition" should "give a move message if its a valid move" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)

    RuleBook.isMoveRuleComplaint(field, turnManager, PlayerNameEnum.GOLD,
      new Position(1, 2), new Position(1, 3)) should
      be(Message.doMove(new Position(1, 2), new Position(1, 3)))
  }

  "isPosFromNotOwn" should "return true, if tile on posFrom is not own" in {
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val field = new Field(Set(), playerSilverTiles)

    PreCondition.isPosFromPosNotOwn(field, PlayerNameEnum.GOLD, new Position(1, 7)) should be(true)

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.RABBIT)
  }
  it should "false if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())

    PreCondition.isPosFromPosNotOwn(field, PlayerNameEnum.GOLD, new Position(1, 2)) should be(false)

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
  }

  "isToPosNotFree" should "return true, if posTo is not free" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    PreCondition.isToPosNotFree(field, new Position(1, 2)) should be(true)
    PreCondition.isToPosNotFree(field, new Position(1, 8)) should be(true)
  }
  it should "false if not" in {
    val field = new Field(Set(), Set())

    PreCondition.isToPosNotFree(field, new Position(1, 3)) should be(false)
  }

  "isWrongRabbitMove" should "return true, if a rabbit gets moved back" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 3)))
    val field = new Field(playerGoldTiles, Set())

    PreCondition.isWrongRabbitMove(field, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 2)) should be(true)
  }
  it should "false if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(2, 3)))
    val field = new Field(playerGoldTiles, Set())

    PreCondition.isWrongRabbitMove(field, PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 2)) should be(false)
  }

  "isTileFreeze" should "return true, if the tile is not surround by own tile but one stronger tile from other player" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.DOG, new Position(4, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(4, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    PreCondition.isTileFreeze(field, PlayerNameEnum.GOLD, new Position(4, 4)) should be(true)
  }
  it should "false if surround by own tile and one stronger tile from other player" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.DOG, new Position(4, 4)),
      new Tile(TileNameEnum.RABBIT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(4, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    PreCondition.isTileFreeze(field, PlayerNameEnum.GOLD, new Position(4, 4)) should be(false)
  }
  it should "false if the tile is not surround by other player tiles" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.DOG, new Position(4, 4)))
    val field = new Field(playerGoldTiles, Set())

    PreCondition.isTileFreeze(field, PlayerNameEnum.GOLD, new Position(4, 4)) should be(false)
  }
  "isTilePush" should "return true, if push tile is other player and surround by stronger other player tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    PreCondition.isTilePush(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5)) should be(true)
  }
  it should "false, if posTo is occupied" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    PreCondition.isTilePush(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(5, 4)) should be(false)
  }
  it should "false if, push tile not surround by other player" in {
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(Set(), playerSilverTiles)

    PreCondition.isTilePush(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5)) should be(false)
  }
  it should "false if push tile not surround by stronger other player" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    PreCondition.isTilePush(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5)) should be(false)
  }
  it should "be null if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val field = new Field(playerGoldTiles, Set())

    PreCondition.isPosFromPosNotOwn(field, PlayerNameEnum.GOLD, new Position(1, 2)) should be(false)
  }

  "isPushNotFinishWithPosTo" should "true, if last move was a push and posTo is not the old pos from push tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val action = ActionCommand(List(PushCommand(field, PlayerNameEnum.SILVER, new Position(5, 5), new Position(6, 5))))

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(action)

    PreCondition.isPushNotFinishWithPosTo(field, new Position(1, 2), turnManager) should be(true)
  }
  it should "false , if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)


    val action = ActionCommand(List(PushCommand(field, PlayerNameEnum.SILVER, new Position(5, 5), new Position(6, 5))))

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(action)

    PreCondition.isPushNotFinishWithPosTo(field, new Position(5, 5), turnManager) should be(false)
  }

  "isTilePull" should "pull the other player tile on the old posFrom last move figures own player" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(4, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(4, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val action = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(4, 4), new Position(5, 4))))

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(action)

    PreCondition.isTilePull(field, new Position(4, 5), new Position(4, 4), turnManager) should be(true)
  }
  it should "false, if old moved tile is not from other player" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(4, 4)),
      new Tile(TileNameEnum.HORSE, new Position(4, 5)))
    val field = new Field(playerGoldTiles, Set())

    val action = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(4, 4), new Position(5, 4))))

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(action)

    PreCondition.isTilePull(field, new Position(4, 5), new Position(4, 4), turnManager) should be(false)
  }
  it should "false, if old moved tile from other player is not strong enough" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.CAT, new Position(4, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(4, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val action = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(4, 4), new Position(5, 4))))

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(action)

    PreCondition.isTilePull(field, new Position(4, 5), new Position(4, 4), turnManager) should be(false)
  }
  it should "false, if posTo is not the last tile posFrom" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(4, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(4, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val action = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(4, 4), new Position(5, 4))))

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)
    turnManager.doAction(action)

    PreCondition.isTilePull(field, new Position(4, 4), new Position(4, 5), turnManager) should be(false)
    PreCondition.isTilePull(field, new Position(4, 4), new Position(4, 3), turnManager) should be(false)
    PreCondition.isTilePull(field, new Position(4, 4), new Position(3, 4), turnManager) should be(false)
    PreCondition.isTilePull(field, new Position(4, 4), new Position(3, 4), turnManager) should be(false)
  }

  "isPosFromEmpty" should "true if on from pos is no tile" in {
    val field = new Field(Set(), Set())
    PreCondition.isPosFromEmpty(field, new Position(4, 4)) should be(true)
  }
  it should "false if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    PreCondition.isPosFromEmpty(field, new Position(1, 1)) should be(false)
    PreCondition.isPosFromEmpty(field, new Position(1, 8)) should be(false)
  }
}
