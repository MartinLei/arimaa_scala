package controller

import controller.impl.ModeEnum.ModeEnum
import model.FieldTrait
import util.position.Position

trait ControllerTrait {
  def changePlayer: String

  def moveTile(posFrom: Position, posTo: Position): List[String]

  def moveTileUndo(): List[String]

  def getFieldAsString: String

  def setMode(mode: ModeEnum, field: FieldTrait): Boolean

  def getMode: ModeEnum
}
