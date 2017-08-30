package controller

import controller.impl.Controller
import controller.impl.messages.imp.{FixTileMessage, MoveMessage, WrongRabbitMoveMessage, WrongToPosMessage}
import model.impl.{PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class ControllerSpec extends FlatSpec with Matchers {


  "A controller" should "give the tile on the given position" in {
    val controller: ControllerTrait = new Controller()
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(1, 1)) should be(TileNameEnum.NONE)
  }

  "toString" should "have given output" in {
    val field99of9String: String = "\n" +
      "  +-----------------+\n" +
      "8 | r r r d d r r r |\n" +
      "7 | r h c e m c h r |\n" +
      "6 |     X     X     |\n" +
      "5 |                 |\n" +
      "4 |                 |\n" +
      "3 |     X     X     |\n" +
      "2 | R H C M E C H R |\n" +
      "1 | R R R D D R R R |\n" +
      "  +-----------------+\n" +
      "    a b c d e f g h  \n"

    val controller: ControllerTrait = new Controller()

    controller.getFieldAsString should be(field99of9String)
  }


  "move" should "move a tile on his given Position" in {
    val controller: ControllerTrait = new Controller()

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    controller.moveTile(new Position(1, 2), new Position(1, 3)) should
      be(new MoveMessage(new Position(1, 2), new Position(1, 3)))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
  }

  it should "not move a tile if toPos is notFree" in {
    val controller: ControllerTrait = new Controller()

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)

    controller.moveTile(new Position(1, 2), new Position(2, 2)) should
      be(new WrongToPosMessage(new Position(2, 2)))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)
  }

  it should "not move a tile if a rabbit moves backward" in {
    val controller: ControllerTrait = new Controller()

    controller.moveTile(new Position(1, 2), new Position(1, 3)) should
      be(new MoveMessage(new Position(1, 2), new Position(1, 3)))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    controller.moveTile(new Position(1, 3), new Position(1, 2)) should
      be(new WrongRabbitMoveMessage)

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
  }


  it should "not move a tile if the tile is fixed by other player stronger tile" in {
    val controller: ControllerTrait = new Controller()

    controller.moveTile(new Position(4, 2), new Position(4, 3)) should
      be(new MoveMessage(new Position(4, 2), new Position(4, 3)))
    controller.moveTile(new Position(4, 3), new Position(4, 4)) should
      be(new MoveMessage(new Position(4, 3), new Position(4, 4)))
    controller.changePlayer()
    controller.moveTile(new Position(4, 7), new Position(4, 6)) should
      be(new MoveMessage(new Position(4, 7), new Position(4, 6)))
    controller.moveTile(new Position(4, 6), new Position(4, 5)) should
      be(new MoveMessage(new Position(4, 6), new Position(4, 5)))
    controller.changePlayer()

    controller.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAMEL)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(4, 5)) should be(TileNameEnum.ELEPHANT)

    controller.moveTile(new Position(4, 4), new Position(5, 4)) should
      be(new FixTileMessage(new Position(4, 5)))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAMEL)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(4, 5)) should be(TileNameEnum.ELEPHANT)
  }

  "changePlayer" should "change the Player" in {
    val controller: ControllerTrait = new Controller()
    controller.getActPlayerName should be(PlayerNameEnum.GOLD)
    controller.changePlayer()
    controller.getActPlayerName should be(PlayerNameEnum.SILVER)
    controller.changePlayer()
    controller.getActPlayerName should be(PlayerNameEnum.GOLD)
  }
}
