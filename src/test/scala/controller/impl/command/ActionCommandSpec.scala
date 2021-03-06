package controller.impl.command

import controller.impl.command.impl.{MoveCommand, PushCommand}
import controller.impl.messages.MessageText
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class ActionCommandSpec extends FlatSpec with Matchers {
  val field = new Field()
  val commandList = List(
    MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)),
    MoveCommand(field, PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3)))

  val actionCommand = ActionCommand(commandList)

  "doAction" should "execute all commands" in {
    val commandMessageListShould = List(
      MessageText.doMove(new Position(1, 2), new Position(1, 3)),
      MessageText.doMove(new Position(2, 2), new Position(2, 3)))

    val commandMessageList = actionCommand.doAction()

    commandMessageList shouldEqual commandMessageListShould
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
  }

  "undoAction" should "execute all undo commands" in {

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)

    val undoMessageListShould = List(
      MessageText.undoMove(new Position(2, 2), new Position(2, 3)),
      MessageText.undoMove(new Position(1, 2), new Position(1, 3)))

    val undoMessageList = actionCommand.undoAction()
    undoMessageList shouldEqual undoMessageListShould

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.NONE)
  }

  "getLastCommandPosFrom" should "give the posFrom, if last command was a move or push command" in {
    val actionCommand1 = ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))
    actionCommand1.getLastCommandPosFrom should be(Some(new Position(1, 2)))

    val actionCommand2 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))
    actionCommand2.getLastCommandPosFrom should be(Some(new Position(1, 2)))
  }
  it should "false, if no move or pus command" in {
    val actionCommand1 = ActionCommand(List())
    actionCommand1.getLastCommandPosFrom should be(Option(null))
  }

  "getLastCommandPosTo" should "give the posTo, if last command was a move or push command" in {
    val actionCommand1 = ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))
    actionCommand1.getLastCommandPosTo should be(Some(new Position(1, 3)))

    val actionCommand2 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))
    actionCommand2.getLastCommandPosTo should be(Some(new Position(1, 3)))
  }
  it should "false, if no move or pus command" in {
    val actionCommand1 = ActionCommand(List())
    actionCommand1.getLastCommandPosTo should be(Option(null))
  }
  "isLastAPushCommand" should "true, if last command was a pushCommand" in {
    val actionCommand1 = ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))
    actionCommand1.isLastAPushCommand should be(true)
  }
  it should "false, if not" in {
    val actionCommand1 = ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))
    actionCommand1.isLastAPushCommand should be(false)
  }
  it should "false, if empty" in {
    val actionCommand1 = ActionCommand(List())
    actionCommand1.isLastAPushCommand should be(false)
  }

  "equals" should "true, if stack is the same" in {
    val actionCommand1 = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)),
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4))))

    val actionCommand2 = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)),
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4))))

    actionCommand1 should be(actionCommand2)
  }
  it should "false, if not same" in {
    val actionCommand1 = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)),
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4))))

    val actionCommand2 = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)),
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(2, 3), new Position(1, 4))))

    actionCommand1 should not be actionCommand2
  }
  it should "false, if class is not a action command" in {
    val actionCommand1 = ActionCommand(List(
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)),
      MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 3), new Position(1, 4))))

    actionCommand1 should not be new Position(1, 1)
  }
}
