package controller.impl.command.impl

import controller.impl.command.CommandTrait
import controller.impl.messages.Message
import model.FieldTrait

case class ChangePlayerCommand(field: FieldTrait) extends CommandTrait {
  override def doCommand(): String = {
    changePlayer()
  }

  private def changePlayer(): String = {
    field.changePlayer()
    Message.changePlayer(field.actualPlayerName)
  }

  override def undoCommand(): String = {
    changePlayer()
  }
}
