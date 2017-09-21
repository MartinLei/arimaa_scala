package controller.impl

import controller.impl.ModeEnum.ModeEnum
import controller.impl.messages.{Message, MessageType}
import util.position.Position

class EndMode extends Mode {
  override val modeType: ModeEnum = ModeEnum.END

  override def changePlayer: MessageType = {
    Message.endGame
  }

  override def moveTile(posFrom: Position, posTo: Position): List[String] = {
    List(Message.endGame.text)
  }

  override def moveTileUndo: List[String] = {
    List(Message.endGame.text)
  }

}
