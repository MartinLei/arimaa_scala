package controller.impl

import controller.impl.messages.MessageType
import util.position.Position

trait Mode {
  def changePlayer(): MessageType

  def moveTile(posFrom: Position, posTo: Position): List[String]

  def moveTileUndo(): List[String]
}
