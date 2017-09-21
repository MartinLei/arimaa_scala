package controller.impl.mode

import controller.impl.messages.MessageType
import controller.impl.mode.ModeEnum.ModeEnum
import util.position.Position

trait Mode {
  def getFieldAsString: String

  val modeType: ModeEnum

  def changePlayer: MessageType

  def moveTile(posFrom: Position, posTo: Position): List[String]

  def moveTileUndo: List[String]

}
