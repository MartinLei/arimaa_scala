package controller.impl

import util.position.Position

trait Mode {
  def changePlayer(): String

  def moveTile(posFrom: Position, posTo: Position): List[String]

  def moveTileUndo(): List[String]
}
