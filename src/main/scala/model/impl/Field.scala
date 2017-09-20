package model.impl

import model.FieldTrait
import model.impl.PlayerNameEnum.PlayerNameEnum
import model.impl.TileNameEnum.TileNameEnum
import util.position.Position

import scala.collection.mutable.ListBuffer


class Field() extends FieldTrait {

  private var playerGold: Player = getInitGoldPlayer
  private var playerSilver: Player = getInitSilverPlayer

  override var actualPlayerName: PlayerNameEnum = PlayerNameEnum.GOLD
  override var winPlayerName: PlayerNameEnum = PlayerNameEnum.NONE

  def this(playerGoldTiles: Set[Tile], playerSilverTiles: Set[Tile]) {
    this()
    this.playerGold = new Player(PlayerNameEnum.GOLD, playerGoldTiles)
    this.playerSilver = new Player(PlayerNameEnum.SILVER, playerSilverTiles)
  }

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

  override def isSurroundByOwnTile(playerName: PlayerNameEnum, posFrom_Ignore: Position, posTo_Observe: Position): Boolean = {
    if (playerName.equals(PlayerNameEnum.NONE))
      return false

    var surround = Position.getSurround(posTo_Observe)
    surround = surround.filter(surPos => !surPos.equals(posFrom_Ignore))

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

  override def changeTilePos(player: PlayerNameEnum, posFrom: Position, posTo: Position): Boolean = {
    if (isOccupied(posTo))
      return false

    player match {
      case PlayerNameEnum.GOLD => playerGold.moveTile(posFrom, posTo)
      case PlayerNameEnum.SILVER => playerSilver.moveTile(posFrom, posTo)
      case PlayerNameEnum.NONE => false
    }
  }

  override def isOccupied(pos: Position): Boolean = {
    if (getTileName(PlayerNameEnum.GOLD, pos) != TileNameEnum.NONE)
      return true
    if (getTileName(PlayerNameEnum.SILVER, pos) != TileNameEnum.NONE)
      return true

    false
  }

  override def getStrongerTilesWhoAround(playerAround: PlayerNameEnum, pos: Position, playerPos: PlayerNameEnum): List[Position] = {
    if (playerAround.equals(PlayerNameEnum.NONE) || playerPos.equals(PlayerNameEnum.NONE))
      return List()

    val surroundPosList = Position.getSurround(pos)
    val posTileName = getTileName(playerPos, pos)

    val strongerSurroundPosList: ListBuffer[Position] = ListBuffer()

    surroundPosList.foreach(surPos => {
      val surPosTileName = getTileName(playerAround, surPos)
      if (surPosTileName.compare(posTileName) > 0)
        strongerSurroundPosList.+=(surPos)
    })

    strongerSurroundPosList.toList
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

  override def removeTile(pos: Position): Boolean = {
    val player = getPlayerName(pos)
    player match {
      case PlayerNameEnum.GOLD => playerGold.remove(pos)
      case PlayerNameEnum.SILVER => playerSilver.remove(pos)
      case PlayerNameEnum.NONE => false
    }
  }

  override def addTile(playerName: PlayerNameEnum, tileName: TileNameEnum, pos: Position): Boolean = {
    if (isOccupied(pos))
      return false

    playerName match {
      case PlayerNameEnum.GOLD => playerGold.add(tileName, pos)
      case PlayerNameEnum.SILVER => playerSilver.add(tileName, pos)
      case PlayerNameEnum.NONE => false
    }
  }

  override def changePlayer(): PlayerNameEnum = {
    actualPlayerName = PlayerNameEnum.getInvertPlayer(actualPlayerName)
    actualPlayerName
  }

  override def hasNoRabbits(playerName: PlayerNameEnum): Boolean = playerName match {
    case PlayerNameEnum.GOLD => playerGold.hasNoRabbits
    case PlayerNameEnum.SILVER => playerSilver.hasNoRabbits
    case _ => false
  }

  override def hasRabbitOnOtherSide(playerName: PlayerNameEnum): Boolean = playerName match {
    case PlayerNameEnum.GOLD => playerGold.hasRabbitOnRow(8)
    case PlayerNameEnum.SILVER => playerSilver.hasRabbitOnRow(1)
    case _ => false
  }
}
