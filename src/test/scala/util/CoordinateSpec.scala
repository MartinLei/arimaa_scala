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
}
