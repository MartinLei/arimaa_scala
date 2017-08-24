package model

import model.PlayerNameEnum.PlayerNameEnum
import util.Position

import scala.collection.mutable

class Field() {
  private val playerGold: Player = getInitGoldPlayer
  private val playerSilver: Player = getInitSilverPlayer

  def getPlayerTiles(player: PlayerNameEnum): mutable.Set[Tile] = player match {
    case PlayerNameEnum.GOLD =>
      playerGold.getTiles
    case PlayerNameEnum.SILVER =>
      playerSilver.getTiles
    case _ =>
      null
  }

  private def getInitGoldPlayer: Player = {
    val tilesGold: mutable.Set[Tile] = new mutable.HashSet()
    tilesGold add new Tile(TileNameEnum.RABBIT, new Position(1, 1))
    //TODO add all tales

    new Player(PlayerNameEnum.GOLD, tilesGold)
  }

  private def getInitSilverPlayer: Player = {
    val tilesSilver: mutable.Set[Tile] = new mutable.HashSet()
    tilesSilver add new Tile(TileNameEnum.RABBIT, new Position(1, 6))
    //TODO add all tales

    new Player(PlayerNameEnum.SILVER, tilesSilver)
  }


}
