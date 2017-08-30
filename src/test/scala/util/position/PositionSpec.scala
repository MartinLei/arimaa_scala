package util.position

import org.scalatest._

class PositionSpec extends FlatSpec with Matchers {

  "A Position" should "throw NoSuchElementException if pos is not between 1,1 - 8,8" in {
    an[IllegalArgumentException] should be thrownBy new Position(1, 9)
    an[IllegalArgumentException] should be thrownBy new Position(9, 1)
    an[IllegalArgumentException] should be thrownBy new Position(-1, 1)
    an[IllegalArgumentException] should be thrownBy new Position(1, -1)
  }

  "getSurround" should "be get a set of surrounded position" in {
    val surround3_3Should = Set(new Position(3, 4), new Position(4, 3), new Position(3, 2), new Position(2, 3))
    val pos3_3 = new Position(3, 3)

    Position.getSurround(pos3_3) should be(surround3_3Should)
  }

  it should "get only two in a corner" in {
    val surround1_1Should = Set(new Position(2, 1), new Position(1, 2))
    val pos1_1 = new Position(1, 1)

    Position.getSurround(pos1_1) should be(surround1_1Should)
  }

  it should "get only three on a side" in {
    val surround1_3Should = Set(new Position(1, 4), new Position(2, 3), new Position(1, 2))
    val pos1_3 = new Position(1, 3)

    Position.getSurround(pos1_3) should be(surround1_3Should)
  }
}
