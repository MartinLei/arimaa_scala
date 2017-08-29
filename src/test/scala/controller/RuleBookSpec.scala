package controller

import controller.impl.RuleBook
import controller.impl.messages.imp.{MoveMessage, WrongFromPosMessage, WrongRabbitMoveMessage, WrongToPosMessage}
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.Position

class RuleBookSpec extends FlatSpec with Matchers {

  "precondition" should "give a MoveMessage if its a valid move" in {
    val ruleBook = new RuleBook(new Field())

    ruleBook.precondition(new Position(1, 2), new Position(1, 3)) should
      be(new MoveMessage(new Position(1, 2), new Position(1, 3)))
  }

  "isFromPosNotOwn" should "return true, if tile on posFrom is not own -> WrongFromPosMessage" in {
    val ruleBook = new RuleBook(new Field())

    ruleBook.precondition(new Position(1, 7), new Position(1, 8)) should be(new WrongFromPosMessage)
  }

  "isToPosNotFree" should "return true, if posTo is not free ->WrongToPosMessage" in {
    val ruleBook = new RuleBook(new Field())

    ruleBook.precondition(new Position(1, 1), new Position(1, 2)) should be(new WrongToPosMessage)
  }

  "isWrongRabbitMove" should "return true if a rabbit gets moved back -> WrongRabbitMoveMessage " in {
    val field = new Field()
    val ruleBook = new RuleBook(field)

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    ruleBook.precondition(new Position(1, 3), new Position(1, 2)) should be(new WrongRabbitMoveMessage)
  }

  it should "return false, if the tile is not a rabbit tile -> MoveMessage" in {
    val field = new Field()
    val ruleBook = new RuleBook(field)

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)

    ruleBook.precondition(new Position(2, 3), new Position(2, 2)) should
      be(new MoveMessage(new Position(2, 3), new Position(2, 2)))
  }
}
