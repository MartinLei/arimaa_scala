package controller.impl

import controller.impl.messages.MessageTrade
import controller.impl.messages.imp.WrongFromPosMessage
import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.{Player, PlayerNameEnum}
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

    new WrongFromPosMessage()
  }

}
