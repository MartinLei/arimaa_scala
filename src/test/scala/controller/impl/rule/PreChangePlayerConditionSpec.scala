package controller.impl.rule

import controller.impl.command.impl.{MoveCommand, PushCommand}
import controller.impl.command.{ActionCommand, ActionManager}
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PreChangePlayerConditionSpec extends FlatSpec with Matchers {

  "isPushNotFinish" should "true if, last command is a push command" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val actionManager = new ActionManager()
    val action = new ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5))))
    actionManager.doAction(action)

    PreChangePlayerCondition.isPushNotFinish(actionManager) should be(true)
  }
  it should "false, if not" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.HORSE, new Position(5, 4)))
    val playerSilverTiles = Set(
      new Tile(TileNameEnum.CAMEL, new Position(5, 5)))
    val field = new Field(playerGoldTiles, playerSilverTiles)

    val actionManager = new ActionManager()
    val action1 = new ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(5, 5), new Position(6, 5))))
    val action2 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(5, 4), new Position(5, 5))))
    actionManager.doAction(action1)
    actionManager.doAction(action2)

    PreChangePlayerCondition.isPushNotFinish(actionManager) should be(false)
  }

}