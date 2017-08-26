package model.impl

import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.Position

import scala.collection.mutable

class Field() extends FieldTrait {
  private val playerGold: Player = getInitGoldPlayer
  private val playerSilver: Player = getInitSilverPlayer

  private def getInitGoldPlayer: Player = {
    val tilesGold: mutable.Set[Tile] = new mutable.HashSet()
    tilesGold add new Tile(TileNameEnum.RABBIT, new Position(1, 1))
    tilesGold add new Tile(TileNameEnum.RABBIT, new Position(2, 1))
    tilesGold add new Tile(TileNameEnum.RABBIT, new Position(3, 1))
    tilesGold add new Tile(TileNameEnum.DOG, new Position(4, 1))
    tilesGold add new Tile(TileNameEnum.DOG, new Position(5, 1))
    tilesGold add new Tile(TileNameEnum.RABBIT, new Position(6, 1))
    tilesGold add new Tile(TileNameEnum.RABBIT, new Position(7, 1))
    tilesGold add new Tile(TileNameEnum.RABBIT, new Position(8, 1))
    tilesGold add new Tile(TileNameEnum.RABBIT, new Position(1, 2))
    tilesGold add new Tile(TileNameEnum.HORSE, new Position(2, 2))
    tilesGold add new Tile(TileNameEnum.CAT, new Position(3, 2))
    tilesGold add new Tile(TileNameEnum.CAMEL, new Position(4, 2))
    tilesGold add new Tile(TileNameEnum.ELEPHANT, new Position(5, 2))
    tilesGold add new Tile(TileNameEnum.CAT, new Position(6, 2))
    tilesGold add new Tile(TileNameEnum.HORSE, new Position(7, 2))
    tilesGold add new Tile(TileNameEnum.RABBIT, new Position(8, 2))

    new Player(PlayerNameEnum.GOLD, tilesGold)
  }

  private def getInitSilverPlayer: Player = {
    val tilesSilver: mutable.Set[Tile] = new mutable.HashSet()
    tilesSilver add new Tile(TileNameEnum.RABBIT, new Position(1, 8))
    tilesSilver add new Tile(TileNameEnum.RABBIT, new Position(2, 8))
    tilesSilver add new Tile(TileNameEnum.RABBIT, new Position(3, 8))
    tilesSilver add new Tile(TileNameEnum.DOG, new Position(4, 8))
    tilesSilver add new Tile(TileNameEnum.DOG, new Position(5, 8))
    tilesSilver add new Tile(TileNameEnum.RABBIT, new Position(6, 8))
    tilesSilver add new Tile(TileNameEnum.RABBIT, new Position(7, 8))
    tilesSilver add new Tile(TileNameEnum.RABBIT, new Position(8, 8))
    tilesSilver add new Tile(TileNameEnum.RABBIT, new Position(1, 7))
    tilesSilver add new Tile(TileNameEnum.HORSE, new Position(2, 7))
    tilesSilver add new Tile(TileNameEnum.CAT, new Position(3, 7))
    tilesSilver add new Tile(TileNameEnum.ELEPHANT, new Position(4, 7))
    tilesSilver add new Tile(TileNameEnum.CAMEL, new Position(5, 7))
    tilesSilver add new Tile(TileNameEnum.CAT, new Position(6, 7))
    tilesSilver add new Tile(TileNameEnum.HORSE, new Position(7, 7))
    tilesSilver add new Tile(TileNameEnum.RABBIT, new Position(8, 7))

    new Player(PlayerNameEnum.SILVER, tilesSilver)
  }


  override def getPlayerTiles(player: PlayerNameEnum): mutable.Set[Tile] = player match {
    case PlayerNameEnum.GOLD =>
      playerGold.getTiles
    case PlayerNameEnum.SILVER =>
      playerSilver.getTiles
  }

  override def toString: String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder()
    sb.append("\n")
    sb.append("  +-----------------+\n")
    sb.append(fieldAsString)
    sb.append("  +-----------------+\n")
    sb.append("    a b c d e f g h  \n")
    sb.toString()
  }

  private def fieldAsString: String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder()
    for (y <- 8 to 1 by -1) {
      sb.append(y + " | ")
      for (x <- 1 until 9) {
        val pos: Position = new util.Position(x, y)
        sb.append(cellAsString(pos))
      }
      sb.append("|\n")
    }
    sb.toString()
  }

  private def cellAsString(pos: Position): String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder()
    val traps: Set[Position] =
      Set(new Position(3, 3), new Position(6, 3),
        new Position(3, 6), new Position(6, 6))

    if (traps.exists((trap: Position) => trap.equals(pos)))
      sb.append("X")
    else
      sb.append(cellTileAsString(pos))

    sb.append(" ")
    sb.toString()
  }

  private def cellTileAsString(pos: Position): String = {
    val sb: mutable.StringBuilder = new mutable.StringBuilder()

    val tileNameGold: TileNameEnum = getTileName(PlayerNameEnum.GOLD, pos)
    val tileNameSilver: TileNameEnum = getTileName(PlayerNameEnum.SILVER, pos)

    if (!tileNameGold.equals(TileNameEnum.NONE))
      sb.append(tileNameGold.toString)
    else if (!tileNameSilver.equals(TileNameEnum.NONE))
      sb.append(tileNameSilver.toString.toLowerCase)
    else
      sb.append(" ")

    sb.toString()
  }

  override def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum = player match {
    case PlayerNameEnum.GOLD => playerGold.getTile(pos)
    case PlayerNameEnum.SILVER => playerSilver.getTile(pos)
  }
}