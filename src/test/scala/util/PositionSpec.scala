package util

import org.scalatest._

class PositionSpec extends FlatSpec with Matchers {

  "A Position" should "have a x and y Coordinates" in {
    val pos = new Position(1, 2)
    pos.x should be(1)
    pos.y should be(2)
  }

  it should "have a string representation" in {
    val pos = new Position(1, 2)
    val posString = "{1,2}"
    pos.toString should be(posString)
  }

}
