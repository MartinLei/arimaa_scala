package controller.impl.rule

import controller.impl.command.TurnManager
import controller.impl.messages.MessageType
import model.FieldTrait
import model.impl.PlayerNameEnum
import model.impl.PlayerNameEnum.PlayerNameEnum
import util.position.Position

object WinCondition {

  def winByKillAllOtherRabbits(field: FieldTrait): PlayerNameEnum = {
    val actPlayerName = field.actualPlayerName
    val pasPlayerName = PlayerNameEnum.getInvertPlayer(actPlayerName)

    if (field.hasNoRabbits(pasPlayerName))
      return actPlayerName
    if (field.hasNoRabbits(actPlayerName))
      return pasPlayerName

    PlayerNameEnum.NONE
  }

  def winByRabbitOnOtherSide(field: FieldTrait): PlayerNameEnum = {
    if (field.hasRabbitOnOtherSide(field.actualPlayerName))
      return field.actualPlayerName

    val pasPlayerName = PlayerNameEnum.getInvertPlayer(field.actualPlayerName)
    if (field.hasRabbitOnOtherSide(pasPlayerName))
      return pasPlayerName

    PlayerNameEnum.NONE
  }

  def winByPassivePlayerCantMove(field: FieldTrait, turnManager: TurnManager): PlayerNameEnum = {
    val passivePlayer = PlayerNameEnum.getInvertPlayer(field.actualPlayerName)

    if (isPlayerNotMoveAble(field, turnManager, passivePlayer))
      return field.actualPlayerName

    PlayerNameEnum.NONE
  }

  private def isPlayerNotMoveAble(field: FieldTrait, turnManager: TurnManager, playerName: PlayerNameEnum): Boolean = {
    val playerTiles = field.getPlayerTiles(playerName)

    val countPossibleMoves: Int = playerTiles.count(tile => {
      val posFrom = tile.pos
      hasPossibleMove(field, turnManager, playerName, posFrom)
    })

    countPossibleMoves == 0
  }

  private def hasPossibleMove(field: FieldTrait, turnManager: TurnManager, playerName: PlayerNameEnum, posFrom: Position): Boolean = {
    val possiblePosTo = Position.getSurround(posFrom)
    val countPossiblePosTo: Int = possiblePosTo.count(posTo => {
      val ruleComplaint: MessageType = RuleBook.isMoveRuleComplaint(field, turnManager, playerName, posFrom, posTo)
      ruleComplaint.isValid
    })

    countPossiblePosTo > 0
  }

}
