package controller.impl.command

import controller.impl.command.impl.MoveCommand
import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, Tile, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PlayerTurnSpec extends FlatSpec with Matchers {

  "A PlayerTurn" should "have a player name" in {
    val playerTurn = PlayerTurn(PlayerNameEnum.GOLD)
    playerTurn.name should be(PlayerNameEnum.GOLD)
  }

  "doAction" should "add action to list and do action it" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val field = new Field(playerGoldTiles, Set())
    val playerTurn = PlayerTurn(PlayerNameEnum.GOLD)

    playerTurn.doAction(ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))) should
      be(List(MessageText.doMove(new Position(1, 1), new Position(1, 2))))
  }

  "undoAction" should "remove action from list and undo action" in {
    val playerGoldTiles = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)))
    val field = new Field(playerGoldTiles, Set())
    val playerTurn = PlayerTurn(PlayerNameEnum.GOLD)

    playerTurn.doAction(ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 1), new Position(1, 2))))) should
      be(List(MessageText.doMove(new Position(1, 1), new Position(1, 2))))

    playerTurn.undoAction should
      be(List(MessageText.undoMove(new Position(1, 1), new Position(1, 2))))
  }
}
