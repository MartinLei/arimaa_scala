package controller.impl

import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.{Player, PlayerNameEnum}

class RuleBook(val field: FieldTrait) {
  private val actPlayer: Player = field.getPlayer(PlayerNameEnum.GOLD)

  def getActPlayerName: PlayerNameEnum = {
    actPlayer.name
  }

}
