package controller.impl.command.impl

import controller.impl.command.CommandTrait
import controller.impl.messages.MessageText
import model.FieldTrait
import model.impl.PlayerNameEnum
import model.impl.PlayerNameEnum.PlayerNameEnum

case class WinCommand(field: FieldTrait, playerName: PlayerNameEnum) extends CommandTrait {
  private val winPlayerName = playerName

  override def doCommand(): String = {
    field.winPlayerName = winPlayerName
    MessageText.doWin(winPlayerName)
  }

  override def undoCommand(): String = {
    field.winPlayerName = PlayerNameEnum.NONE
    MessageText.undoWin(winPlayerName)
  }
}
