package controller

import controller.impl.messages.MessageTrade
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.Position

trait ControllerTrait {
  def changePlayer(): Unit

  def getActPlayerName: PlayerNameEnum

  def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum

  def moveTile(posFrom: Position, posTo: Position): MessageTrade

  def getFieldAsString: String
}
