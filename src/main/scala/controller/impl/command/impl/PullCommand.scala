package controller.impl.command.impl

import controller.impl.command.CommandTrait
import controller.impl.messages.MessageText
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

case class PullCommand(field: FieldTrait, playerName: PlayerNameEnum, posFrom: Position, posTo: Position) extends CommandTrait {
  override def doCommand(): String = {
    if (!field.changeTilePos(playerName, posFrom, posTo))
      return MessageText.errorChangeTile(playerName, posFrom, posTo)

    MessageText.doPull(posFrom, posTo)
  }

  override def undoCommand(): String = {
    field.changeTilePos(playerName, posTo, posFrom)
    MessageText.undoPull(posFrom, posTo)
  }

  override def equals(that: Any): Boolean = that match {
    case that: PullCommand => that.isInstanceOf[PullCommand] && that.hashCode() == this.hashCode()
    case _ => false
  }

  override def hashCode(): Int = toString.hashCode

  override def toString: String = "{" + playerName + " pull" + " from" + posFrom + " to" + posTo + "}"
}
