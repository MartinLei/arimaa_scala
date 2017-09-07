package controller.impl.rule

import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PreconditionSpec extends FlatSpec with Matchers {

  "precondition" should "give a move message if its a valid move" in {
    val ruleBook = new RuleBook(new Field())

    ruleBook.isMoveRuleComplaint(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)) should be(RuleEnum.MOVE)
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

  "isTilePull" should "return true, if pull tile is other player and surround by stronger other player tile" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(5, 2), new Position(5, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(5, 7), new Position(5, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.ELEPHANT)
    field.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.CAMEL)

    Precondition.isTailPull(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5)) should be(true)

  }
  it should "false if, pull tile not surround by other player" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.SILVER, new Position(5, 7), new Position(5, 5))

    field.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.CAMEL)

    Precondition.isTailPull(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5)) should be(false)
  }
  it should "fals if pull tile not surround by stronger other player" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(5, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(5, 7), new Position(5, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.CAMEL)

    Precondition.isTailPull(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5)) should be(false)
  }
  it should "be null if not" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)

    Precondition.isFromPosNotOwn(field, PlayerNameEnum.GOLD, new Position(1, 2)) should be(false)

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
  }

}
