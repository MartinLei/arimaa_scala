package controller.impl


import controller.impl.command.TurnManager
import controller.impl.messages.{Message, MessageText}
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class GameModeSpec extends FlatSpec with Matchers {


  "A gameMode" should "give the tile on the given position" in {
    val gameMode = new GameMode(new Field(), new TurnManager(PlayerNameEnum.GOLD))
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(1, 1)) should be(TileNameEnum.NONE)
  }
  it should "have a constructor to can set specific tiles" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(1, 1)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(1, 1)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(1, 8)) should be(TileNameEnum.RABBIT)
  }

  "move" should "move a tile on his given Position" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)

    gameMode.moveTile(new Position(1, 1), new Position(1, 2)) should
      be(List(MessageText.doMove(new Position(1, 1), new Position(1, 2))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
  }

  "posFromNotOwn" should "not move a tile if posFrom is not own tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(1, 8)) should be(TileNameEnum.RABBIT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.NONE)

    gameMode.moveTile(new Position(1, 8), new Position(1, 7)) should
      be(List(MessageText.wrongPosFrom(new Position(1, 8))))

    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(1, 8)) should be(TileNameEnum.RABBIT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(1, 7)) should be(TileNameEnum.NONE)
  }
  "posToNotFree" should "not move a tile if posTo is not free" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)),
      new Tile(TileNameEnum.HORSE, new Position(1, 2)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.HORSE)

    gameMode.moveTile(new Position(1, 1), new Position(1, 2)) should
      be(List(MessageText.wrongPosTo(new Position(1, 2))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 1)) should be(TileNameEnum.RABBIT)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.HORSE)
  }

  "rabbitMove" should "not move a tile if a rabbit moves backward" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 2), new Position(1, 3)) should
      be(List(MessageText.doMove(new Position(1, 2), new Position(1, 3))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    gameMode.moveTile(new Position(1, 3), new Position(1, 2)) should
      be(List(MessageText.wrongRabbitMove))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
  }

  "tileFreeze" should "not move a tile if the tile is freeze by other stronger player tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(4, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(4, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(4, 4), new Position(5, 4)) should
      be(List(MessageText.freezeTile))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(4, 4)) should be(TileNameEnum.CAMEL)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(4, 5)) should be(TileNameEnum.ELEPHANT)
  }

  "tileTrapped" should "remove tile if it is trapped" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.CAT, new Position(3, 2)))
    val field = new Field(playerGoldTiles, Set())
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(3, 2), new Position(3, 3)) should
      be(List(
        MessageText.doMove(new Position(3, 2), new Position(3, 3)),
        MessageText.doTrap(new Position(3, 3))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)
  }
  it should "not remove tile if it is trapped but surround by own tiles" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.CAT, new Position(3, 2)),
      new Tile(TileNameEnum.DOG, new Position(2, 3)))
    val field = new Field(playerGoldTiles, Set())
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(3, 2), new Position(3, 3)) should
      be(List(MessageText.doMove(new Position(3, 2), new Position(3, 3))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.DOG)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.CAT)
  }
  "tileTrapUndo" should "respawn the figure" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.CAT, new Position(3, 2)))
    val field = new Field(playerGoldTiles, Set())
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(3, 2), new Position(3, 3)) should
      be(List(
        MessageText.doMove(new Position(3, 2), new Position(3, 3)),
        MessageText.doTrap(new Position(3, 3))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)

    gameMode.moveTileUndo() should
      be(List(
        MessageText.undoTrap(new Position(3, 3)),
        MessageText.undoMove(new Position(3, 2), new Position(3, 3))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.CAT)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)
  }

  "tileNowTrapped" should "remove a own tile from trap,if actual move frees the tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(2, 3)),
      new Tile(TileNameEnum.CAT, new Position(3, 3)))
    val field = new Field(playerGoldTiles, Set())
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(2, 3), new Position(2, 4)) should
      be(List(
        MessageText.doMove(new Position(2, 3), new Position(2, 4)),
        MessageText.doTrap(new Position(3, 3))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.HORSE)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)
  }

  it should "remove tile from trap, if its now not surround by own tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(2, 3)),
      new Tile(TileNameEnum.CAT, new Position(3, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(2, 4)),
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(3, 2), new Position(3, 3))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.SILVER))

    gameMode.moveTile(new Position(2, 4), new Position(3, 4)) should
      be(List(MessageText.doMove(new Position(2, 4), new Position(3, 4))))

    gameMode.moveTile(new Position(2, 3), new Position(2, 4)) should
      be(List(
        MessageText.doPull(new Position(2, 3), new Position(2, 4)),
        MessageText.doTrap(new Position(3, 3))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.HORSE)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(3, 4)) should be(TileNameEnum.CAMEL)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)
  }

  "tileNowTrappedUndo" should "respawn tile from trap, if get surrounded" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(2, 3)),
      new Tile(TileNameEnum.CAT, new Position(3, 3)))
    val field = new Field(playerGoldTiles, Set())
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(2, 3), new Position(2, 4)) should
      be(List(
        MessageText.doMove(new Position(2, 3), new Position(2, 4)),
        MessageText.doTrap(new Position(3, 3))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.HORSE)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)

    gameMode.moveTileUndo() should
      be(List(
        MessageText.undoTrap(new Position(3, 3)),
        MessageText.undoMove(new Position(2, 3), new Position(2, 4))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.CAT)
  }
  it should "respawn tile from trap, if now surrounded by undo pull" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(2, 3)),
      new Tile(TileNameEnum.CAT, new Position(3, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(2, 4)),
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(3, 2), new Position(3, 3))

    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.SILVER))

    gameMode.moveTile(new Position(2, 4), new Position(3, 4)) should
      be(List(MessageText.doMove(new Position(2, 4), new Position(3, 4))))

    gameMode.moveTile(new Position(2, 3), new Position(2, 4)) should
      be(List(
        MessageText.doPull(new Position(2, 3), new Position(2, 4)),
        MessageText.doTrap(new Position(3, 3))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.HORSE)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(3, 4)) should be(TileNameEnum.CAMEL)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)

    gameMode.moveTileUndo() should
      be(List(
        MessageText.undoTrap(new Position(3, 3)),
        MessageText.undoPull(new Position(2, 3), new Position(2, 4))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(2, 4)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(3, 4)) should be(TileNameEnum.CAMEL)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)
  }

  "pushTile" should "push a other player tile, if it surround by player stronger tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(5, 5), new Position(5, 4)) should
      be(List(MessageText.wrongPosTo(new Position(5, 4))))

    gameMode.moveTile(new Position(5, 5), new Position(6, 5)) should
      be(List(MessageText.doPush(new Position(5, 5), new Position(6, 5))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.ELEPHANT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(6, 5)) should be(TileNameEnum.CAMEL)
  }
  it should "first finish push before moving other tiles" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(5, 5), new Position(6, 5)) should
      be(List(MessageText.doPush(new Position(5, 5), new Position(6, 5))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.ELEPHANT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(6, 5)) should be(TileNameEnum.CAMEL)

    gameMode.moveTile(new Position(5, 4), new Position(4, 4)) should
      be(List(MessageText.pushNotFinish))
  }

  "pushTileUndo" should "push tile back" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(5, 5), new Position(5, 4)) should
      be(List(MessageText.wrongPosTo(new Position(5, 4))))

    gameMode.moveTile(new Position(5, 5), new Position(6, 5)) should
      be(List(MessageText.doPush(new Position(5, 5), new Position(6, 5))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.ELEPHANT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(6, 5)) should be(TileNameEnum.CAMEL)

    gameMode.moveTileUndo() should
      be(List(MessageText.undoPush(new Position(5, 5), new Position(6, 5))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.ELEPHANT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.CAMEL)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(6, 5)) should be(TileNameEnum.NONE)
  }
  it should "undo half push" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(5, 5), new Position(6, 5)) should
      be(List(MessageText.doPush(new Position(5, 5), new Position(6, 5))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.ELEPHANT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(6, 5)) should be(TileNameEnum.CAMEL)

    gameMode.moveTileUndo() should
      be(List(MessageText.undoPush(new Position(5, 5), new Position(6, 5))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(5, 4)) should be(TileNameEnum.ELEPHANT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.CAMEL)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(6, 5)) should be(TileNameEnum.NONE)

    gameMode.moveTile(new Position(5, 4), new Position(4, 4)) should
      be(List(MessageText.doMove(new Position(5, 4), new Position(4, 4))))
  }

  "pullTile" should "pull a other player tile, if posTo is the same as the old posFrom last moved tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(5, 4), new Position(6, 4)) should
      be(List(MessageText.doMove(new Position(5, 4), new Position(6, 4))))

    gameMode.moveTile(new Position(5, 5), new Position(5, 4)) should
      be(List(MessageText.doPull(new Position(5, 5), new Position(5, 4))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(6, 4)) should be(TileNameEnum.ELEPHANT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(5, 4)) should be(TileNameEnum.CAMEL)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.NONE)
  }
  "pullTileUndo" should "undo pull" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(5, 4), new Position(6, 4)) should
      be(List(MessageText.doMove(new Position(5, 4), new Position(6, 4))))

    gameMode.moveTile(new Position(5, 5), new Position(5, 4)) should
      be(List(MessageText.doPull(new Position(5, 5), new Position(5, 4))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(6, 4)) should be(TileNameEnum.ELEPHANT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(5, 4)) should be(TileNameEnum.CAMEL)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.NONE)

    gameMode.moveTileUndo() should
      be(List(MessageText.undoPull(new Position(5, 5), new Position(5, 4))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(6, 4)) should be(TileNameEnum.ELEPHANT)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(5, 4)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.SILVER, new Position(5, 5)) should be(TileNameEnum.CAMEL)
  }

  "fromPosEmpty" should "do nothing, if pos from is empty" in {
    val field = new Field(Set(), Set())
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))
    gameMode.moveTile(new Position(1, 1), new Position(1, 2)) should
      be(List(MessageText.posFromEmpty(new Position(1, 1))))
  }

  "changePlayer" should "change the Player" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.getActPlayerName should be(PlayerNameEnum.GOLD)

    gameMode.moveTile(new Position(1, 2), new Position(1, 3))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.SILVER))

    gameMode.getActPlayerName should be(PlayerNameEnum.SILVER)

    gameMode.moveTile(new Position(1, 7), new Position(1, 6))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.GOLD))
    gameMode.getActPlayerName should be(PlayerNameEnum.GOLD)
  }
  it should "do nothing, if actual player not finish his move" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(5, 5), new Position(6, 5)) should
      be(List(MessageText.doPush(new Position(5, 5), new Position(6, 5))))

    gameMode.changePlayer() should be(Message.pushNotFinish)
    gameMode.getActPlayerName should be(PlayerNameEnum.GOLD)
  }
  it should "do nothing, if first move of game not move any tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.changePlayer() should be(Message.noTileMoved)
    gameMode.getActPlayerName should be(PlayerNameEnum.GOLD)
  }
  it should "do nothing, if actual player not move any tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 2), new Position(1, 3))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.SILVER))
    gameMode.changePlayer() should be(Message.noTileMoved)
    gameMode.getActPlayerName should be(PlayerNameEnum.SILVER)
  }
  it should "do nothing, if actual move is third time repetition" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.DOG, new Position(1, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(8, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 1), new Position(1, 2))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.SILVER))
    gameMode.moveTile(new Position(8, 8), new Position(8, 7))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 2), new Position(1, 1))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.SILVER))
    gameMode.moveTile(new Position(8, 7), new Position(8, 6))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 1), new Position(1, 2))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.SILVER))
    gameMode.moveTile(new Position(8, 6), new Position(8, 5))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 2), new Position(1, 1))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.SILVER))
    gameMode.moveTile(new Position(8, 5), new Position(8, 4))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 1), new Position(1, 2))
    gameMode.changePlayer() should be(Message.thirdTimeRepetition)

    gameMode.moveTile(new Position(1, 2), new Position(1, 3))
    gameMode.changePlayer() should be(Message.changePlayer(PlayerNameEnum.SILVER))
  }

  "RabbitReachOtherSide" should "gold win, if a rabbit reach the other side" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 7), new Position(1, 8))

    gameMode.getWinnerName should be(PlayerNameEnum.GOLD)
  }
  it should "silver win, if a rabbit reach the other side" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 1)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 1), new Position(1, 2))

    gameMode.getWinnerName should be(PlayerNameEnum.SILVER)
  }
  it should "actual player win, if both player rabbit reach the other side" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 1)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 7), new Position(1, 8))

    gameMode.getWinnerName should be(PlayerNameEnum.GOLD)
  }

  "PlayerHasNoRabbit" should "gold win, if silver has no rabbits" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 1), new Position(1, 2))

    gameMode.getWinnerName should be(PlayerNameEnum.GOLD)
  }
  it should "silver win, if gold has no rabbits" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(3, 2)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(3, 5)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(3, 2), new Position(3, 3)) should
      be(List(
        MessageText.doMove(new Position(3, 2), new Position(3, 3)),
        MessageText.doTrap(new Position(3, 3))))

    gameMode.getWinnerName should be(PlayerNameEnum.SILVER)
  }

  "PassivePlayerCantMakeAMove" should "active player win, if passive player cant move any tile" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 5)),
      new Tile(TileNameEnum.DOG, new Position(2, 4)),
      new Tile(TileNameEnum.DOG, new Position(1, 2)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 4)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 2), new Position(1, 3))

    gameMode.getWinnerName should be(PlayerNameEnum.GOLD)
  }
  "unDo" should "undo last move" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)
    val gameMode = new GameMode(field, new TurnManager(PlayerNameEnum.GOLD))

    gameMode.moveTile(new Position(1, 2), new Position(1, 3)) should
      be(List(MessageText.doMove(new Position(1, 2), new Position(1, 3))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)

    gameMode.moveTile(new Position(1, 3), new Position(1, 4)) should
      be(List(MessageText.doMove(new Position(1, 3), new Position(1, 4))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 4)) should be(TileNameEnum.RABBIT)

    gameMode.moveTileUndo() should be(List(MessageText.undoMove(new Position(1, 3), new Position(1, 4))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 4)) should be(TileNameEnum.NONE)

    gameMode.moveTileUndo() should be(List(MessageText.undoMove(new Position(1, 2), new Position(1, 3))))

    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    gameMode.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
  }

}
