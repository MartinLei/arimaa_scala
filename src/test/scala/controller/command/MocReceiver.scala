package controller.command

import util.position.Position

class MocReceiver(var pos: Position) {
  def add(value: Int): Unit = {
    pos = new Position(pos.x + value, pos.y + value)
  }
}
