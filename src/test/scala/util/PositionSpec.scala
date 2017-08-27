package util

import org.scalatest._

class PositionSpec extends FlatSpec with Matchers {

  "A Position" should "have a x and y Coordinates" in {
    val pos = new Position(1, 2)

    pos.x should be(1)
    pos.y should be(2)
  }

  it should "throw NoSuchElementException if pos is not between 1,1 - 8,8" in {
    an[IllegalArgumentException] should be thrownBy new Position(1, 9)
    an[IllegalArgumentException] should be thrownBy new Position(9, 1)
    an[IllegalArgumentException] should be thrownBy new Position(-1, 1)
    an[IllegalArgumentException] should be thrownBy new Position(1, -1)
  }

  "toString" should "have given output" in {
    val pos = new Position(1, 2)
    val posString = "{1,2}"

    pos.toString should be(posString)
  }

  "equal" should "objects, if it has the same coordinates" in {
    val pos1: Position = new Position(1, 2)
    val pos2: Position = new Position(1, 2)

    pos1 should be(pos2)
  }

  it should "not equal, if it has not the same coordinates" in {
    val pos1: Position = new Position(1, 2)
    val pos2: Position = new Position(1, 3)

    pos1 should not be pos2
  }

  it should "not equal, if it has not a position object" in {
    val pos1: Position = new Position(1, 2)
    pos1 should not be 1
  }
}
