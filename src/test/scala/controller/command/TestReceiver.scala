package controller.command

class TestReceiver(var sum: Int) {
  def add(value: Int): Unit = {
    sum += value
  }
}
