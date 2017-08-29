package model.impl

object PlayerNameEnum extends Enumeration {
  type PlayerNameEnum = Value
  val GOLD: Value = Value("G")
  val SILVER: Value = Value("S")

  def getInvertPlayer(player: PlayerNameEnum): PlayerNameEnum = player match {
    case PlayerNameEnum.GOLD => PlayerNameEnum.SILVER
    case PlayerNameEnum.SILVER => PlayerNameEnum.GOLD
  }
}
