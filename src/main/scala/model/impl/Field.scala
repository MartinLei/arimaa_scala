package model.impl

import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.Position


class Field() extends FieldTrait {
  private val playerGold: Player = getInitGoldPlayer
  private val playerSilver: Player = getInitSilverPlayer

  override def getPlayerTiles(player: PlayerNameEnum): Set[Tile] = player match {
    case PlayerNameEnum.GOLD =>
      playerGold.getTiles
    case PlayerNameEnum.SILVER =>
      playerSilver.getTiles
  }

  private def getInitGoldPlayer: Player = {
    val tilesGold: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(2, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(3, 1)),
      new Tile(TileNameEnum.DOG, new Position(4, 1)),
      new Tile(TileNameEnum.DOG, new Position(5, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(6, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(7, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(8, 1)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 2)),
      new Tile(TileNameEnum.HORSE, new Position(2, 2)),
      new Tile(TileNameEnum.CAT, new Position(3, 2)),
      new Tile(TileNameEnum.CAMEL, new Position(4, 2)),
      new Tile(TileNameEnum.ELEPHANT, new Position(5, 2)),
      new Tile(TileNameEnum.CAT, new Position(6, 2)),
      new Tile(TileNameEnum.HORSE, new Position(7, 2)),
      new Tile(TileNameEnum.RABBIT, new Position(8, 2)))

    new Player(PlayerNameEnum.GOLD, tilesGold)
  }

  private def getInitSilverPlayer: Player = {
    val tilesSilver: Set[Tile] = Set(
      new Tile(TileNameEnum.RABBIT, new Position(1, 8)),
      new Tile(TileNameEnum.RABBIT, new Position(2, 8)),
      new Tile(TileNameEnum.RABBIT, new Position(3, 8)),
      new Tile(TileNameEnum.DOG, new Position(4, 8)),
      new Tile(TileNameEnum.DOG, new Position(5, 8)),
      new Tile(TileNameEnum.RABBIT, new Position(6, 8)),
      new Tile(TileNameEnum.RABBIT, new Position(7, 8)),
      new Tile(TileNameEnum.RABBIT, new Position(8, 8)),
      new Tile(TileNameEnum.RABBIT, new Position(1, 7)),
      new Tile(TileNameEnum.HORSE, new Position(2, 7)),
      new Tile(TileNameEnum.CAT, new Position(3, 7)),
      new Tile(TileNameEnum.ELEPHANT, new Position(4, 7)),
      new Tile(TileNameEnum.CAMEL, new Position(5, 7)),
      new Tile(TileNameEnum.CAT, new Position(6, 7)),
      new Tile(TileNameEnum.HORSE, new Position(7, 7)),
      new Tile(TileNameEnum.RABBIT, new Position(8, 7)))

    new Player(PlayerNameEnum.SILVER, tilesSilver)
  }

  override def toString: String = {
    val sb: StringBuilder = new StringBuilder()
    sb.append("\n")
    sb.append("  +-----------------+\n")
    sb.append(fieldAsString)
    sb.append("  +-----------------+\n")
    sb.append("    a b c d e f g h  \n")
    sb.toString()
  }

  private def fieldAsString: String = {
    val sb: StringBuilder = new StringBuilder()
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
    val sb: StringBuilder = new StringBuilder()
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
    val sb: StringBuilder = new StringBuilder()

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
    case PlayerNameEnum.GOLD => playerGold.getTileName(pos)
    case PlayerNameEnum.SILVER => playerSilver.getTileName(pos)
  }

  override def moveTile(player: PlayerNameEnum, posFrom: Position, posTo: Position): Boolean = player match {
    case PlayerNameEnum.GOLD => playerGold.moveTile(posFrom, posTo)
    case PlayerNameEnum.SILVER => playerSilver.moveTile(posFrom, posTo)
  }
}
