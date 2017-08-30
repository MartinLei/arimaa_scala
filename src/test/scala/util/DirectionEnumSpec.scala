package util

import org.scalatest.{FlatSpec, Matchers}
import util.position.Position


class DirectionEnumSpec extends FlatSpec with Matchers {

  "Direction" should "have this elements with string representation" in {
    DirectionEnum.NORTH.toString should be("n")
    DirectionEnum.EAST.toString should be("e")
    DirectionEnum.SOUTH.toString should be("s")
    DirectionEnum.WEST.toString should be("w")

  }

  "getDirection" should "get the direction in witch tow points are" in {
    DirectionEnum.getDirection(new Position(4, 4), new Position(4, 5)) should be(DirectionEnum.NORTH)
    DirectionEnum.getDirection(new Position(4, 4), new Position(5, 4)) should be(DirectionEnum.EAST)
    DirectionEnum.getDirection(new Position(4, 4), new Position(4, 3)) should be(DirectionEnum.SOUTH)
    DirectionEnum.getDirection(new Position(4, 4), new Position(3, 4)) should be(DirectionEnum.WEST)
  }

  it should "get none if the position to far away" in {
    DirectionEnum.getDirection(new Position(4, 4), new Position(1, 4)) should be(DirectionEnum.NONE)
  }
}
