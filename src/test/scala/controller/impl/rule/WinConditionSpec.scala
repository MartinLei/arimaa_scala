package controller.impl.rule

import controller.impl.command.TurnManager
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class WinConditionSpec extends FlatSpec with Matchers {


  "winByKillAllOtherRabbits" should "gold if silver has no rabbits on field" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val field = new Field(playerGoldTiles, Set())

    WinCondition.winByKillAllOtherRabbits(field) should be(PlayerNameEnum.GOLD)
  }
  it should "silver if gold has no rabbits on field" in {
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)))
    val field = new Field(Set(), playerSilverTiles)

    field.changePlayer()

    WinCondition.winByKillAllOtherRabbits(field) should be(PlayerNameEnum.SILVER)
  }
  it should "passive player, if active player remove own rabbit" in {
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)))
    val field = new Field(Set(), playerSilverTiles)

    WinCondition.winByKillAllOtherRabbits(field) should be(PlayerNameEnum.SILVER)
  }
  it should "NONE if gold and silver has a rabbit" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    WinCondition.winByKillAllOtherRabbits(field) should be(PlayerNameEnum.NONE)
  }

  "winByRabbitOnOtherSide" should "gold if a gold rabbit is on the other side" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    WinCondition.winByRabbitOnOtherSide(field) should be(PlayerNameEnum.GOLD)
  }
  it should "silver if a silver rabbit is on the other side" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 1)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    WinCondition.winByRabbitOnOtherSide(field) should be(PlayerNameEnum.SILVER)
  }
  it should "NONE if both have no rabbit on the other side" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    WinCondition.winByRabbitOnOtherSide(field) should be(PlayerNameEnum.NONE)
  }
  it should "act Player if both have a rabbit on the other side" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 1)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    field.actualPlayerName = PlayerNameEnum.SILVER
    WinCondition.winByRabbitOnOtherSide(field) should be(PlayerNameEnum.SILVER)
  }

  "winByPassivePlayerCantMoveW" should "active player, if passive player tile can not move" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.CAT, new Position(1, 5)),
      new Tile(TileNameEnum.DOG, new Position(2, 4)),
      new Tile(TileNameEnum.DOG, new Position(1, 3)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 4)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)

    WinCondition.winByPassivePlayerCantMove(field, turnManager) should be(PlayerNameEnum.GOLD)
  }
  it should "NONE if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val turnManager = new TurnManager()
    turnManager.addTurn(PlayerNameEnum.GOLD)

    WinCondition.winByPassivePlayerCantMove(field, turnManager) should be(PlayerNameEnum.NONE)
  }

}
