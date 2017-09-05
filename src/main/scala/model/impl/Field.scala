package model.impl

import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.position.Position


class Field() extends FieldTrait {
  private val playerGold: Player = getInitGoldPlayer
  private val playerSilver: Player = getInitSilverPlayer

  override def getPlayerTiles(player: PlayerNameEnum): Set[Tile] = player match {
    case PlayerNameEnum.GOLD => playerGold.getTiles
    case PlayerNameEnum.SILVER => playerSilver.getTiles
    case PlayerNameEnum.NONE => Set()
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
        val pos: Position = new Position(x, y)
        sb.append(cellAsString(pos))
      }
      sb.append("|\n")
    }
    sb.toString()
  }

  private def cellAsString(pos: Position): String = {
    val sb: StringBuilder = new StringBuilder()

    val cellTileString = cellTileAsString(pos)
    if (Position.isPosATrap(pos) && cellTileString.equals(" "))
      sb.append("X")
    else
      sb.append(cellTileString)

    sb.append(" ")
    sb.toString()
  }

  override def isSurroundByOwnTile(playerName: PlayerNameEnum, posFrom: Position, posTo: Position): Boolean = {
    if (playerName.equals(PlayerNameEnum.NONE))
      return false

    var surround = Position.getSurround(posTo)
    surround = surround.filter(surPos => !surPos.equals(posFrom))

    surround.foreach(surPos => {
      val surPlayerName = getPlayerName(surPos)
      if (playerName.equals(surPlayerName))
        return true
    })

    false
  }

  override def getTileName(player: PlayerNameEnum, pos: Position): TileNameEnum = player match {
    case PlayerNameEnum.GOLD => playerGold.getTileName(pos)
    case PlayerNameEnum.SILVER => playerSilver.getTileName(pos)
    case PlayerNameEnum.NONE => TileNameEnum.NONE
  }

  override def changeTilePos(player: PlayerNameEnum, posFrom: Position, posTo: Position): Boolean = player match {
    case PlayerNameEnum.GOLD => playerGold.moveTile(posFrom, posTo)
    case PlayerNameEnum.SILVER => playerSilver.moveTile(posFrom, posTo)
    case PlayerNameEnum.NONE => false
  }

  override def isOccupied(pos: Position): Boolean = {
    if (getTileName(PlayerNameEnum.GOLD, pos) != TileNameEnum.NONE)
      return true
    if (getTileName(PlayerNameEnum.SILVER, pos) != TileNameEnum.NONE)
      return true

    false
  }

  override def getStrongerOtherTilesWhoAround(player: PlayerNameEnum, pos: Position): Option[Position] = {
    val surround = Position.getSurround(pos)
    val posTileName = getTileName(player, pos)
    val pasPlayer = PlayerNameEnum.getInvertPlayer(player)

    surround.foreach(surPos => {
      val surPosTileName = getTileName(pasPlayer, surPos)
      if (surPosTileName.compare(posTileName) > 0)
        return Option(surPos)
    })
    Option(null)
  }

  private def cellTileAsString(pos: Position): String = {
    val sb: StringBuilder = new StringBuilder()

    val tilePlayer = getPlayerName(pos)

    if (tilePlayer.equals(PlayerNameEnum.GOLD))
      sb.append(getTileName(PlayerNameEnum.GOLD, pos).toString)
    else if (tilePlayer.equals(PlayerNameEnum.SILVER))
      sb.append(getTileName(PlayerNameEnum.SILVER, pos).toString.toLowerCase)
    else
      sb.append(" ")

    sb.toString()
  }

  override def getPlayerName(pos: Position): PlayerNameEnum = {
    if (!playerGold.getTileName(pos).equals(TileNameEnum.NONE))
      return PlayerNameEnum.GOLD
    else if (!playerSilver.getTileName(pos).equals(TileNameEnum.NONE))
      return PlayerNameEnum.SILVER

    PlayerNameEnum.NONE
  }

  override def removeTile(pos: Position): Unit = {
    val player = getPlayerName(pos)
    player match {
      case PlayerNameEnum.GOLD => playerGold.remove(pos)
      case PlayerNameEnum.SILVER => playerSilver.remove(pos)
      case PlayerNameEnum.NONE =>
    }
  }

  override def addTile(playerName: PlayerNameEnum, tileName: TileNameEnum, pos: Position): Unit = {
    if (isOccupied(pos))
      return

    playerName match {
      case PlayerNameEnum.GOLD => playerGold.add(tileName, pos)
      case PlayerNameEnum.SILVER => playerSilver.add(tileName, pos)
      case PlayerNameEnum.NONE =>
    }
  }
}
