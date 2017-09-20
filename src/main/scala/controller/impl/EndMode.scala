package controller.impl

import controller.impl.messages.{Message, MessageType}
import model.impl.PlayerNameEnum
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

class EndMode extends Mode {

  override def changePlayer: MessageType = {
    Message.endGame
  }

  override def moveTile(posFrom: Position, posTo: Position): List[String] = {
    List(Message.endGame.text)
  }

  override def moveTileUndo: List[String] = {
    List(Message.endGame.text)
  }

  override def getWinnerName: PlayerNameEnum = {
    PlayerNameEnum.NONE
  }
}
