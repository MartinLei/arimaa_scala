package util.position

import org.scalatest._

class PositionRawSpec extends FlatSpec with Matchers {

  "A Position" should "have a x and y Coordinates" in {
    val pos = new PositionRaw(1, 2)

    pos.x should be(1)
    pos.y should be(2)
  }

  "toString" should "have given output" in {
    val pos = new PositionRaw(1, 2)
    val posString = "{1,2}"

    pos.toString should be(posString)
  }

  "equal" should "objects, if it has the same coordinates" in {
    val pos1 = new PositionRaw(1, 2)
    val pos2 = new PositionRaw(1, 2)

    pos1 should be(pos2)
  }

  it should "not equal, if it has not the same coordinates" in {
    val pos1 = new PositionRaw(1, 2)
    val pos2 = new PositionRaw(1, 3)

    pos1 should not be pos2
  }

  it should "not equal, if it has not a position object" in {
    val pos1 = new PositionRaw(1, 2)
    pos1 should not be 1
  }

  "isPosYEquals" should "true, if y is equals to given pos" in {
    val pos = new Position(1, 2)
    pos.isPosYEquals(2)
  }
}
