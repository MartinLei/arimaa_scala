package controller.impl

import controller.impl.messages.MessageTrade
import controller.impl.messages.imp.{MoveMessage, WrongFromPosMessage}
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.{Player, PlayerNameEnum, TileNameEnum}
import util.Position

class RuleBook(val field: FieldTrait) {
  private var playerView: Player = field.getPlayer(PlayerNameEnum.GOLD)

  def getActPlayerName: PlayerNameEnum = {
    playerView.name
  }

  def setRuleView(playerName: PlayerNameEnum): Unit = {
    playerView = field.getPlayer(playerName)
  }

  def precondition(posFrom: Position, posTo: Position): MessageTrade = {
    if (playerView.getTileName(posFrom).equals(TileNameEnum.NONE))
      return new WrongFromPosMessage


    new MoveMessage(posFrom, posTo)
  }

}
