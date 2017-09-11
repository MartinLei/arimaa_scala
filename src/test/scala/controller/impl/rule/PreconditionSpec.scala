package controller.impl.rule

import controller.impl.command.impl.{MoveCommand, PushCommand}
import controller.impl.command.{ActionCommand, UndoActionManager}
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PreconditionSpec extends FlatSpec with Matchers {

  "precondition" should "give a move message if its a valid move" in {
    val field = new Field()
    val undoActionManager = new UndoActionManager
    val ruleBook = RuleBook()

    ruleBook.isMoveRuleComplaint(field, undoActionManager, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)) should be(RuleEnum.MOVE)
  }

  "isFromPosNotOwn" should "return true, if tile on posFrom is not own" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.RABBIT)

    Precondition.isFromPosNotOwn(field, PlayerNameEnum.GOLD, new Position(1, 7)) should be(true)

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.RABBIT)
  }
  it should "false if not" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)

    Precondition.isFromPosNotOwn(field, PlayerNameEnum.GOLD, new Position(1, 2)) should be(false)

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
  }

  "isToPosNotFree" should "return true, if posTo is not free" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)

    Precondition.isToPosNotFree(field, PlayerNameEnum.GOLD, new Position(1, 2)) should be(true)

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 8)) should be(TileNameEnum.RABBIT)

    Precondition.isToPosNotFree(field, PlayerNameEnum.GOLD, new Position(1, 8)) should be(true)
  }
  it should "false if not" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    Precondition.isToPosNotFree(field, PlayerNameEnum.GOLD, new Position(1, 3)) should be(false)
  }

  "isWrongRabbitMove" should "return true, if a rabbit gets moved back" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    Precondition.isWrongRabbitMove(field, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 2)) should be(true)
  }

  it should "false if not" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)

    Precondition.isWrongRabbitMove(field, PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 2)) should be(false)
  }

  "isTailFixed" should "return true, if the tail is surround by one stronger tile from other player" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(4, 2), new Position(4, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(4, 7), new Position(4, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAMEL)
    field.getTileName(PlayerNameEnum.SILVER, new Position(4, 5)) should be(TileNameEnum.ELEPHANT)

    Precondition.isTailFixed(field, PlayerNameEnum.GOLD, new Position(4, 4)) should be(true)
  }

  it should "false if not" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 2)) should be(TileNameEnum.CAMEL)

    Precondition.isTailFixed(field, PlayerNameEnum.GOLD, new Position(4, 2)) should be(false)
  }

  "isTailPush" should "return true, if push tile is other player and surround by stronger other player tile" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(5, 2), new Position(5, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(5, 7), new Position(5, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.ELEPHANT)
    field.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.CAMEL)

    Precondition.isTailPush(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5)) should be(true)

  }
  it should "false, if posTo is occupied" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(5, 2), new Position(5, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(5, 7), new Position(5, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.ELEPHANT)
    field.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.CAMEL)

    Precondition.isTailPush(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(5, 4)) should be(false)
  }
  it should "false if, push tile not surround by other player" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.SILVER, new Position(5, 7), new Position(5, 5))

    field.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.CAMEL)

    Precondition.isTailPush(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5)) should be(false)
  }
  it should "false if push tile not surround by stronger other player" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(5, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(5, 7), new Position(5, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.CAMEL)

    Precondition.isTailPush(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5)) should be(false)
  }
  it should "be null if not" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)

    Precondition.isFromPosNotOwn(field, PlayerNameEnum.GOLD, new Position(1, 2)) should be(false)

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
  }

  "isPushNotFinish" should "true, if last move was a push and posTo is not the old pos from push tile" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(5, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(5, 7), new Position(5, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.CAMEL)

    val undoActionManager = new UndoActionManager()
    val action = new ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5))))
    undoActionManager.doAction(action)

    Precondition.isPushNotFinish(field, PlayerNameEnum.GOLD, new Position(1, 2), undoActionManager) should be(true)
  }
  it should "false , if not" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(5, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(5, 7), new Position(5, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.CAMEL)

    val undoActionManager = new UndoActionManager()
    val action = new ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5))))
    undoActionManager.doAction(action)

    Precondition.isPushNotFinish(field, PlayerNameEnum.GOLD, new Position(5, 5), undoActionManager) should be(false)
  }

  "isTilePull" should "pull the other player tile on the old posFrom last move figures own player" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(4, 2), new Position(4, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(2, 7), new Position(4, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAMEL)
    field.getTileName(PlayerNameEnum.SILVER, new Position(4, 5)) should be(TileNameEnum.HORSE)

    val undoActionManager = new UndoActionManager()
    val action = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(4, 4), new Position(5, 4))))
    undoActionManager.doAction(action)

    Precondition.isTilePull(field, new Position(4, 5), new Position(4, 4), undoActionManager) should be(true)
  }
  it should "false, if old moved tile is not from other player" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(4, 2), new Position(4, 4))
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(4, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAMEL)
    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 5)) should be(TileNameEnum.HORSE)

    val undoActionManager = new UndoActionManager()
    val action = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(4, 4), new Position(5, 4))))
    undoActionManager.doAction(action)

    Precondition.isTilePull(field, new Position(4, 5), new Position(4, 4), undoActionManager) should be(false)
  }
  it should "false, if old moved tile from other player is not strong enough" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(3, 2), new Position(4, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(2, 7), new Position(4, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAT)
    field.getTileName(PlayerNameEnum.SILVER, new Position(4, 5)) should be(TileNameEnum.HORSE)

    val undoActionManager = new UndoActionManager()
    val action = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(4, 4), new Position(5, 4))))
    undoActionManager.doAction(action)

    Precondition.isTilePull(field, new Position(4, 5), new Position(4, 4), undoActionManager) should be(false)
  }
  it should "false, if posTo is not the last tile posFrom" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(4, 2), new Position(4, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(2, 7), new Position(4, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAMEL)
    field.getTileName(PlayerNameEnum.SILVER, new Position(4, 5)) should be(TileNameEnum.HORSE)

    val undoActionManager = new UndoActionManager()
    val action = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(4, 4), new Position(5, 4))))
    undoActionManager.doAction(action)

    Precondition.isTilePull(field, new Position(4, 4), new Position(4, 5), undoActionManager) should be(false)
    Precondition.isTilePull(field, new Position(4, 4), new Position(4, 3), undoActionManager) should be(false)
    Precondition.isTilePull(field, new Position(4, 4), new Position(3, 4), undoActionManager) should be(false)
    Precondition.isTilePull(field, new Position(4, 4), new Position(3, 4), undoActionManager) should be(false)
  }

}
