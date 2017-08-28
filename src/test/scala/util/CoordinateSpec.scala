package util

import org.scalatest.{FlatSpec, Matchers}

class CoordinateSpec extends FlatSpec with Matchers {

  "coordinatesToPosition" should "convert ( a1 - h1) to a Position object" in {
    Coordinate.toPosition("a1") should be(new Position(1, 1))
    Coordinate.toPosition("h8") should be(new Position(8, 8))
  }
}
