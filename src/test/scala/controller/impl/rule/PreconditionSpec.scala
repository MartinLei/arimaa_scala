package controller.impl.rule

import controller.impl.messages.impl._
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PreconditionSpec extends FlatSpec with Matchers {

  "precondition" should "give a MoveMessage if its a valid move" in {
    val ruleBook = new RuleBook(new Field())

    ruleBook.isMoveRuleComplaint(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)) should
      be(new MoveMessage(new Position(1, 2), new Position(1, 3)))
  }

  "isFromPosNotOwn" should "return WrongFromPosMessage, if tile on posFrom is not own" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.RABBIT)

    Precondition.isFromPosNotOwn(field, PlayerNameEnum.GOLD, new Position(1, 7)) should
      be(Some(new WrongFromPosMessage(new Position(1, 7))))

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.RABBIT)
  }
  it should "be null if not" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)

    Precondition.isFromPosNotOwn(field, PlayerNameEnum.GOLD, new Position(1, 2)) should
      be(Option(null))

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
  }

  "isToPosNotFree" should "return WrongToPosMessage, if posTo is not free" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)

    Precondition.isToPosNotFree(field, PlayerNameEnum.GOLD, new Position(1, 2)) should
      be(Some(new WrongToPosMessage(new Position(1, 2))))

    field.getTileName(PlayerNameEnum.SILVER, new Position(1, 8)) should be(TileNameEnum.RABBIT)

    Precondition.isToPosNotFree(field, PlayerNameEnum.GOLD, new Position(1, 8)) should
      be(Some(new WrongToPosMessage(new Position(1, 8))))
  }
  it should "be null if not" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    Precondition.isToPosNotFree(field, PlayerNameEnum.GOLD, new Position(1, 3)) should
      be(Option(null))
  }

  "isWrongRabbitMove" should "return WrongRabbitMove, if a rabbit gets moved back" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    Precondition.isWrongRabbitMove(field, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 2)) should
      be(Some(new WrongRabbitMoveMessage))
  }

  it should "be null if not" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)

    Precondition.isWrongRabbitMove(field, PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 2)) should
      be(Option(null))
  }

  "isTailFixed" should "give FixTailMessage if the tail is surround by one stronger tile from other player" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(4, 2), new Position(4, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(4, 7), new Position(4, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAMEL)
    field.getTileName(PlayerNameEnum.SILVER, new Position(4, 5)) should be(TileNameEnum.ELEPHANT)

    Precondition.isTailFixed(field, PlayerNameEnum.GOLD, new Position(4, 4)) should
      be(Some(new FixTileMessage(new Position(4, 5))))
  }

  it should "be null if not" in {
    val field = new Field()

    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 2)) should be(TileNameEnum.CAMEL)

    Precondition.isTailFixed(field, PlayerNameEnum.GOLD, new Position(4, 2)) should
      be(Option(null))
  }

  "isTileTrapped" should "give TileTrappedMessage, if the tail is trapped" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(3, 2), new Position(3, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.CAT)

    Precondition.isTileTrapped(field, PlayerNameEnum.GOLD, new Position(3, 2), new Position(3, 3)) should
      be(Some(new TileTrappedMessage(new Position(3, 3))))
  }

  it should "be null if pos is not a trap position" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)

    Precondition.isTileTrapped(field, PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3)) should
      be(Option(null))
  }

  it should "be null if pos is surround by one own tile" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.CAT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)

    Precondition.isTileTrapped(field, PlayerNameEnum.GOLD, new Position(3, 2), new Position(3, 3)) should
      be(Option(null))
  }

}
