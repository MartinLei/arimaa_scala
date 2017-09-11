package controller.impl.command

import controller.impl.command.impl.{MoveCommand, PushCommand}
import controller.impl.messages.Message
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class ActionCommandSpec extends FlatSpec with Matchers {
  val field = new Field()
  val commandList = List(
    MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3)),
    MoveCommand(field, PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3)))

  val actionCommand = new ActionCommand(commandList)

  "doAction" should "execute all commands" in {
    val commandMessageListShould = List(
      Message.doMove(new Position(1, 2), new Position(1, 3)),
      Message.doMove(new Position(2, 2), new Position(2, 3)))

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
      Message.undoMove(new Position(2, 2), new Position(2, 3)),
      Message.undoMove(new Position(1, 2), new Position(1, 3)))

    val undoMessageList = actionCommand.undoAction()
    undoMessageList shouldEqual undoMessageListShould

    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 2)) should be(TileNameEnum.RABBIT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 2)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(1, 3)) should be(TileNameEnum.NONE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.NONE)
  }

  "getPushCommandPosFrom" should "give the first command on list" in {
    val actionCommand1 = new ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))
    actionCommand1.getPushCommandPosFrom should be(Some(new Position(1, 2)))
  }
  it should "null if first command is no push command" in {
    actionCommand.getPushCommandPosFrom should be(Option(null))
  }
  it should "null if empty" in {
    val actionCommand1 = new ActionCommand(List())
    actionCommand1.getPushCommandPosFrom should be(Option(null))
  }

  "isCommandFromPosEqual" should "true, if given pos equal command fromPos" in {
    val actionCommand1 = new ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))
    actionCommand1.isCommandFromPosEqual(new Position(1, 2)) should be(true)

    val actionCommand2 = new ActionCommand(List(MoveCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))
    actionCommand2.isCommandFromPosEqual(new Position(1, 2)) should be(true)
  }
  it should "false, if not equal" in {
    val actionCommand1 = new ActionCommand(List(PushCommand(field, PlayerNameEnum.GOLD, new Position(1, 2), new Position(1, 3))))
    actionCommand1.isCommandFromPosEqual(new Position(1, 3)) should be(false)
  }

}
