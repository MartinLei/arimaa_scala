package util

import org.scalatest.{FlatSpec, Matchers}

class CoordinateSpec extends FlatSpec with Matchers {

  "toPosition" should "convert ( a1 - h1) to a Position object" in {
    Coordinate.toPosition("a1") should be(new Position(1, 1))
    Coordinate.toPosition("h8") should be(new Position(8, 8))
  }

  "posToCoordinate" should "convert position to coordinate" in {
    Coordinate.posToCoordinate(new Position(1, 2)) should be("a2")
    Coordinate.posToCoordinate(new Position(8, 8)) should be("h8")
  }

  "moveToCoordinate" should "convert from and to pos to coordinate" in {
    Coordinate.moveToCoordinate(new Position(4, 4), new Position(4, 5)) should be("d4n")
    Coordinate.moveToCoordinate(new Position(4, 4), new Position(5, 4)) should be("d4e")
    Coordinate.moveToCoordinate(new Position(4, 4), new Position(4, 3)) should be("d4s")
    Coordinate.moveToCoordinate(new Position(4, 4), new Position(3, 4)) should be("d4w")

    Coordinate.moveToCoordinate(new Position(4, 4), new Position(1, 4)) should be("d4NONE")
  }
}
