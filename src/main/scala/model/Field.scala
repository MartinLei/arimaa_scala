package model

import model.PlayerNameEnum.PlayerNameEnum
import model.TileNameEnum.TileNameEnum
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
  }

  private def getInitGoldPlayer: Player = {
    val tilesGold: mutable.Set[Tile] = new mutable.HashSet()
    tilesGold add new Tile(TileNameEnum.RABBIT, new Position(1, 1))
    //TODO add all tales

    new Player(PlayerNameEnum.GOLD, tilesGold)
  }

  override def toString: String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder()
    sb.append("\n")
    sb.append("  +-------------SILVER------------+\n")
    sb.append(fieldAsString)
    sb.append("  +-------------GOLD--------------+\n")
    sb.append("    1   2   3   4   5   6   7   8  \n")
    sb.toString()
  }

  def fieldAsString: String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder()
    for (y <- 8 to 1 by -1) {
      sb.append(y + " ")
      for (x <- 1 until 9) {
        val pos: Position = new util.Position(x, y)
        sb.append(cellAsString(pos))
      }
      sb.append("|\n")
      if (y > 1)
        sb.append("  +---+---+---+---+---+---+---+---+\n")
    }
    sb.toString()
  }

  def cellAsString(pos: Position): String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder()
    val traps: Set[Position] =
      Set(new Position(3, 3), new Position(6, 3),
        new Position(3, 6), new Position(6, 6))


    sb.append("| ")
    if (traps.exists((trap: Position) => trap.equals(pos)))
      sb.append("#")
    else
      sb.append(cellTileAsString(pos))

    sb.append(" ")
    sb.toString()
  }

  def cellTileAsString(pos: Position): String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder()

    val tileNameGold: TileNameEnum = getTileName(PlayerNameEnum.GOLD, pos)
    val tileNameSilver: TileNameEnum = getTileName(PlayerNameEnum.SILVER, pos)

    if (!tileNameGold.equals(TileNameEnum.NONE))
      sb.append(tileNameGold.toString.toLowerCase)
    else if (!tileNameSilver.equals(TileNameEnum.NONE))
      sb.append(tileNameSilver.toString)
    else
      sb.append(" ")

    sb.toString()
  }

  def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum = player match {
    case PlayerNameEnum.GOLD => playerGold.getTile(pos)
    case PlayerNameEnum.SILVER => playerSilver.getTile(pos)
  }

  private def getInitSilverPlayer: Player = {
    val tilesSilver: mutable.Set[Tile] = new mutable.HashSet()
    tilesSilver add new Tile(TileNameEnum.RABBIT, new Position(1, 8))
    //TODO add all tales

    new Player(PlayerNameEnum.SILVER, tilesSilver)
  }

}
