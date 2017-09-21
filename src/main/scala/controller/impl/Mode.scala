package controller.impl

import controller.impl.ModeEnum.ModeEnum
import controller.impl.messages.MessageType
import util.position.Position

trait Mode {
  val modeType: ModeEnum

  def changePlayer: MessageType

  def moveTile(posFrom: Position, posTo: Position): List[String]

  def moveTileUndo: List[String]

}
