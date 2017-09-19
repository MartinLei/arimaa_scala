package controller.impl.command

import controller.impl.command.impl.MoveCommand
import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class TurnManagerSpec extends FlatSpec with Matchers {

  "addTurn" should "add it to stack" in {
    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)
  }

  "doAction" should "add it to last turn and execute action" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val field = new Field(playerGoldTiles, Set())
    val actionCommand = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))

    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)

    turnManager.doAction(actionCommand) should
      be(List(MessageText.doMove(new Position(1, 1), new Position(1, 2))))
  }
  "undoAction" should "undo the action" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val field = new Field(playerGoldTiles, Set())
    val actionCommand = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))

    val turnManager = new TurnManager
    turnManager.addTurn(PlayerNameEnum.GOLD)

    turnManager.doAction(actionCommand) should
      be(List(MessageText.doMove(new Position(1, 1), new Position(1, 2))))
    turnManager.undoAction should
      be(List(MessageText.undoMove(new Position(1, 1), new Position(1, 2))))
    turnManager.undoAction should
      be(List(MessageText.emptyStack))
  }

}


