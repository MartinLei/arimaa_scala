package controller

import controller.impl.RuleBook
import controller.impl.messages.imp._
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.Position

class RuleBookSpec extends FlatSpec with Matchers {

  "precondition" should "give a MoveMessage if its a valid move" in {
    val ruleBook = new RuleBook(new Field())

    ruleBook.precondition(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)) should
      be(new MoveMessage(new Position(1, 2), new Position(1, 3)))
  }

  "isFromPosNotOwn" should "return true, if tile on posFrom is not own -> WrongFromPosMessage" in {
    val ruleBook = new RuleBook(new Field())

    ruleBook.precondition(PlayerNameEnum.GOLD, new Position(1, 7), new Position(1, 8)) should be(new WrongFromPosMessage)
  }

  "isToPosNotFree" should "return true, if posTo is not free ->WrongToPosMessage" in {
    val ruleBook = new RuleBook(new Field())

    ruleBook.precondition(PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2)) should be(new WrongToPosMessage)
  }

  "isWrongRabbitMove" should "return WrongRabbitMove, if a rabbit gets moved back" in {
    val field = new Field()
    val ruleBook = new RuleBook(field)

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    ruleBook.isWrongRabbitMove(PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 2)) should
      be(Some(new WrongRabbitMoveMessage))
  }

  it should "be null if not" in {
    val field = new Field()
    val ruleBook = new RuleBook(field)

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)

    ruleBook.isWrongRabbitMove(PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 2)) should
      be(Option(null))
  }

  "isTailFixed" should "give FixTailMessage if the tail is surround by one stronger tile from other player" in {
    val field = new Field()
    val ruleBook = new RuleBook(field)

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(4, 2), new Position(4, 4))
    field.changeTilePos(PlayerNameEnum.SILVER, new Position(4, 7), new Position(4, 5))

    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAMEL)
    field.getTileName(PlayerNameEnum.SILVER, new Position(4, 5)) should be(TileNameEnum.ELEPHANT)

    ruleBook.isTailFixed(PlayerNameEnum.GOLD, new Position(4, 4)) should
      be(Some(new FixTileMessage(new Position(4, 5))))
  }

  it should "be null if not" in {
    val field = new Field()
    val ruleBook = new RuleBook(field)

    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 2)) should be(TileNameEnum.CAMEL)

    ruleBook.isTailFixed(PlayerNameEnum.GOLD, new Position(4, 2)) should
      be(Option(null))
  }

}
