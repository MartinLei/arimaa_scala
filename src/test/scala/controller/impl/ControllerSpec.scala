package controller.impl

import controller.ControllerTrait
import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class ControllerSpec extends FlatSpec with Matchers {


  "A controller" should "give the tile on the given position" in {
    val controller: ControllerTrait = new Controller()
    controller.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    controller.getTileName(PlayerNameEnum.SILVER, new Position(1, 1)) should be(TileNameEnum.NONE)
  }

  "setMode" should "set mode" in {
    val controller = new Controller()
    controller.getMode should be(ModeEnum.GAME)
    controller.setMode(ModeEnum.GAME, new Field())
    controller.getMode should be(ModeEnum.GAME)
  }

  "RabbitReachOtherSide" should "gold win, if a rabbit reach the other side" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val controller = new Controller()
    controller.setMode(ModeEnum.GAME, field)

    controller.moveTile(new Position(1, 7), new Position(1, 8))

    controller.changePlayer() should
      be(List(
        MessageText.changePlayer(PlayerNameEnum.SILVER),
        MessageText.doWin(PlayerNameEnum.GOLD)))
  }
  it should "silver win, if a rabbit reach the other side" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 1)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val controller = new Controller()
    controller.setMode(ModeEnum.GAME, field)

    controller.moveTile(new Position(1, 1), new Position(1, 2))

    controller.changePlayer() should
      be(List(
        MessageText.changePlayer(PlayerNameEnum.SILVER),
        MessageText.doWin(PlayerNameEnum.SILVER)))
  }
  it should "actual player win, if both player rabbit reach the other side" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 1)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val controller = new Controller()
    controller.setMode(ModeEnum.GAME, field)

    controller.moveTile(new Position(1, 7), new Position(1, 8))

    controller.changePlayer() should
      be(List(
        MessageText.changePlayer(PlayerNameEnum.SILVER),
        MessageText.doWin(PlayerNameEnum.GOLD)))
  }

  "PlayerHasNoRabbit" should "gold win, if silver has no rabbits" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val controller = new Controller()
    controller.setMode(ModeEnum.GAME, field)

    controller.moveTile(new Position(1, 1), new Position(1, 2))

    controller.changePlayer() should be(
      List(
        MessageText.changePlayer(PlayerNameEnum.SILVER),
        MessageText.doWin(PlayerNameEnum.GOLD)))
  }
  it should "silver win, if gold has no rabbits" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(3, 2)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(3, 5)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val controller = new Controller()
    controller.setMode(ModeEnum.GAME, field)

    controller.moveTile(new Position(3, 2), new Position(3, 3)) should
      be(List(
        MessageText.doMove(new Position(3, 2), new Position(3, 3)),
        MessageText.doTrap(new Position(3, 3))))

    controller.changePlayer() should be(
      List(
        MessageText.changePlayer(PlayerNameEnum.SILVER),
        MessageText.doWin(PlayerNameEnum.SILVER)))
  }

  "PassivePlayerCantMakeAMove" should "active player win, if passive player cant move any tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 5)),
      new Tile(TileNameEnum.DOG, new Position(2, 4)),
      new Tile(TileNameEnum.DOG, new Position(1, 2)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 4)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val controller = new Controller()
    controller.setMode(ModeEnum.GAME, field)

    controller.moveTile(new Position(1, 2), new Position(1, 3))

    controller.changePlayer() should be(
      List(
        MessageText.doWin(PlayerNameEnum.GOLD)))
  }

}
