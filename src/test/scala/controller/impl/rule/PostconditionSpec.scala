package controller.impl.rule

import controller.impl.messages.impl.RemoveMessageMessage
import model.impl.{Field, PlayerNameEnum, TileNameEnum}
import org.scalatest.{FlatSpec, Matchers}
import util.position.Position

class PostconditionSpec extends FlatSpec with Matchers {

  "isTileTrapped" should "give TileTrappedMessage, if the tail is trapped" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(3, 2), new Position(3, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.CAT)

    Postcondition.isTileTrapped(field, PlayerNameEnum.GOLD, new Position(3, 2), new Position(3, 3)) should
      be(Some(new RemoveMessageMessage(new Position(3, 3))))
  }

  it should "be null if pos is not a trap position" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)

    Postcondition.isTileTrapped(field, PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3)) should
      be(Option(null))
  }

  it should "be null if pos is surround by one own tile" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 2)) should be(TileNameEnum.CAT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)

    Postcondition.isTileTrapped(field, PlayerNameEnum.GOLD, new Position(3, 2), new Position(3, 3)) should
      be(Option(null))
  }

  "isATileNoTrapped" should "true, if own figure stands on trap and is now not surround by any own tile" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(3, 2), new Position(3, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.CAT)

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 4))

    Postcondition.isATileNoTrapped(field, PlayerNameEnum.GOLD, new Position(2, 3)) should
      be(Some(new RemoveMessageMessage(new Position(3, 3))))
  }
  it should "true, if own figure stands on another trap and is now not surround by any own tile" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(5, 2), new Position(5, 3))
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(6, 2), new Position(6, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(5, 3)) should be(TileNameEnum.ELEPHANT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(6, 3)) should be(TileNameEnum.CAT)

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(5, 3), new Position(5, 4))

    Postcondition.isATileNoTrapped(field, PlayerNameEnum.GOLD, new Position(5, 3)) should
      be(Some(new RemoveMessageMessage(new Position(6, 3))))
  }
  it should "false, if own figure stands on trap but still surround by one own tile" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(3, 2), new Position(3, 3))
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(4, 2), new Position(4, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.CAT)
    field.getTileName(PlayerNameEnum.GOLD, new Position(4, 3)) should be(TileNameEnum.CAMEL)

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 4))

    Postcondition.isATileNoTrapped(field, PlayerNameEnum.GOLD, new Position(2, 3)) should be(Option(null))
  }
  it should "false, if on tile is NONE tile" in {
    val field = new Field()

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 4))

    Postcondition.isATileNoTrapped(field, PlayerNameEnum.GOLD, new Position(2, 3)) should be(Option(null))
  }
  it should "false, if player is NONE" in {
    val field = new Field()
    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 2), new Position(2, 3))
    field.getTileName(PlayerNameEnum.GOLD, new Position(2, 3)) should be(TileNameEnum.HORSE)
    field.getTileName(PlayerNameEnum.GOLD, new Position(3, 3)) should be(TileNameEnum.NONE)

    field.changeTilePos(PlayerNameEnum.GOLD, new Position(2, 3), new Position(2, 4))

    Postcondition.isATileNoTrapped(field, PlayerNameEnum.NONE, new Position(2, 3)) should be(Option(null))
  }


}
