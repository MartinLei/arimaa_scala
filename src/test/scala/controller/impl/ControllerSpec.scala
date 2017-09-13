package controller.impl

import controller.ControllerTrait
import controller.impl.messages.Message
import model.impl.{PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class ControllerSpec extends FlatSpec with Matchers {


  "A controller" should "give the tile on the given position" in {
    val controller: ControllerTrait = new Controller()
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(1, 1)) should be(TileNameEnum.NONE)
  }
  it should "have a constructor to can set specific tiles" in {
    val playerGoldTiles = Set(new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val controller = new Controller(playerGoldTiles, playerSilverTiles)

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(1, 1)) should be(TileNameEnum.NONE)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(1, 1)) should be(TileNameEnum.NONE)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(1, 8)) should be(TileNameEnum.RABBIT)
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
    val controller = new Controller()

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)

    controller.moveTile(new Position(1, 2), new Position(1, 3)) should
      be(List(Message.doMove(new Position(1, 2), new Position(1, 3))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
  }

  it should "not move a tile if posFrom is not own tile" in {
    val controller = new Controller()

    controller.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(1, 6)) should be(TileNameEnum.NONE)

    controller.moveTile(new Position(1, 7), new Position(1, 6)) should
      be(List(Message.wrongPosFrom(new Position(1, 7))))

    controller.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(1, 6)) should be(TileNameEnum.NONE)
  }
  it should "not move a tile if toPos is notFree" in {
    val controller = new Controller()

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)

    controller.moveTile(new Position(1, 2), new Position(2, 2)) should
      be(List(Message.wrongPosTo(new Position(2, 2))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)
  }

  it should "not move a tile if a rabbit moves backward" in {
    val controller = new Controller()

    controller.moveTile(new Position(1, 2), new Position(1, 3)) should
      be(List(Message.doMove(new Position(1, 2), new Position(1, 3))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    controller.moveTile(new Position(1, 3), new Position(1, 2)) should
      be(List(Message.wrongRabbitMove))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
  }

  it should "not move a tile if the tile is freeze by other stronger player tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(4, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(4, 5)))
    val controller = new Controller(playerGoldTiles, playerSilverTiles)

    controller.moveTile(new Position(4, 4), new Position(5, 4)) should
      be(List(Message.freezeTile))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAMEL)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(4, 5)) should be(TileNameEnum.ELEPHANT)
  }
  it should "remove tile if it is trapped" in {
    val controller = new Controller()

    controller.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.CAT)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)

    controller.moveTile(new Position(3, 2), new Position(3, 3)) should
      be(List(
        Message.doMove(new Position(3, 2), new Position(3, 3)),
        Message.doTrap(new Position(3, 3))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.NONE)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)
  }
  it should "not remove tile if it is trapped but surround by own tiles" in {
    val controller = new Controller()

    controller.moveTile(new Position(2, 2), new Position(2, 3)) should
      be(List(Message.doMove(new Position(2, 2), new Position(2, 3))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.CAT)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)

    controller.moveTile(new Position(3, 2), new Position(3, 3)) should
      be(List(Message.doMove(new Position(3, 2), new Position(3, 3))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.NONE)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.CAT)

  }

  it should "remove a own tile from trap,if actual move frees the tile and test undo move" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(2, 3)),
      new Tile(TileNameEnum.CAT, new Position(3, 3)))
    val controller = new Controller(playerGoldTiles, Set())

    controller.moveTile(new Position(2, 3), new Position(2, 4)) should
      be(List(
        Message.doMove(new Position(2, 3), new Position(2, 4)),
        Message.doTrap(new Position(3, 3))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.HORSE)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)

    val undoMessageList = controller.moveTileUndo()
    val undoMessageListShould = List(
      Message.undoTrap(new Position(3, 3)),
      Message.undoMove(new Position(2, 3), new Position(2, 4)))

    undoMessageList shouldEqual undoMessageListShould
    controller.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.CAT)
  }

  it should "remove tile from trap if its not surround, it gets pulled" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(2, 3)),
      new Tile(TileNameEnum.CAT, new Position(3, 3)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(2, 4)))
    val controller = new Controller(playerGoldTiles, playerSilverTiles)

    controller.changePlayer()

    controller.moveTile(new Position(2, 4), new Position(3, 4)) should
      be(List(Message.doMove(new Position(2, 4), new Position(3, 4))))

    controller.moveTile(new Position(2, 3), new Position(2, 4)) should
      be(List(
        Message.doPull(new Position(2, 3), new Position(2, 4)),
        Message.doTrap(new Position(3, 3))))
  }

  it should "push a other player tile, if it surround by player stronger tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val controller = new Controller(playerGoldTiles, playerSilverTiles)

    controller.moveTile(new Position(5, 5), new Position(5, 4)) should
      be(List(Message.wrongPosTo(new Position(5, 4))))

    controller.moveTile(new Position(5, 5), new Position(6, 5)) should
      be(List(Message.doPush(new Position(5, 5), new Position(6, 5))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.ELEPHANT)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.NONE)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(6, 5)) should be(TileNameEnum.CAMEL)
  }
  it should "first finish push before moving other tiles" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val controller = new Controller(playerGoldTiles, playerSilverTiles)

    controller.moveTile(new Position(5, 5), new Position(6, 5)) should
      be(List(Message.doPush(new Position(5, 5), new Position(6, 5))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.ELEPHANT)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.NONE)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(6, 5)) should be(TileNameEnum.CAMEL)

    controller.moveTile(new Position(5, 4), new Position(4, 4)) should
      be(List(Message.pushNotFinish))
  }
  it should "pull a other player tile, if posTo is the same as the old posFrom last moved tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val controller = new Controller(playerGoldTiles, playerSilverTiles)

    controller.moveTile(new Position(5, 4), new Position(6, 4)) should
      be(List(Message.doMove(new Position(5, 4), new Position(6, 4))))

    controller.moveTile(new Position(5, 5), new Position(5, 4)) should
      be(List(Message.doPull(new Position(5, 5), new Position(5, 4))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(6, 4)) should be(TileNameEnum.ELEPHANT)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(5, 4)) should be(TileNameEnum.CAMEL)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.NONE)
  }

  it should "do nothing, if pos from is empty" in {
    val controller = new Controller(Set(), Set())
    controller.moveTile(new Position(1, 1), new Position(1, 2)) should
      be(List(Message.posFromEmpty(new Position(1, 1))))
  }


  "changePlayer" should "change the Player" in {
    val controller: ControllerTrait = new Controller()
    controller.getActPlayerName should be(PlayerNameEnum.GOLD)
    controller.changePlayer()
    controller.getActPlayerName should be(PlayerNameEnum.SILVER)
    controller.changePlayer()
    controller.getActPlayerName should be(PlayerNameEnum.GOLD)
  }

  "unDo" should "undo last move" in {
    val controller: ControllerTrait = new Controller()

    controller.moveTile(new Position(1, 2), new Position(1, 3)) should
      be(List(Message.doMove(new Position(1, 2), new Position(1, 3))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    controller.moveTile(new Position(1, 3), new Position(1, 4)) should
      be(List(Message.doMove(new Position(1, 3), new Position(1, 4))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 4)) should be(TileNameEnum.RABBIT)

    controller.moveTileUndo() should be(List(Message.undoMove(new Position(1, 3), new Position(1, 4))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 4)) should be(TileNameEnum.NONE)

    controller.moveTileUndo() should be(List(Message.undoMove(new Position(1, 2), new Position(1, 3))))

    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
  }
}
