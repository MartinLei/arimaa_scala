package model.impl

object PlayerNameEnum extends Enumeration {
  type PlayerNameEnum = Value
  val GOLD: Value = Value("Gold")
  val SILVER: Value = Value("Silver")
  val NONE: Value = Value("NONE")

  def getInvertPlayer(player: PlayerNameEnum): PlayerNameEnum = player match {
    case PlayerNameEnum.GOLD => PlayerNameEnum.SILVER
    case PlayerNameEnum.SILVER => PlayerNameEnum.GOLD
    case PlayerNameEnum.NONE => PlayerNameEnum.NONE
  }
}
