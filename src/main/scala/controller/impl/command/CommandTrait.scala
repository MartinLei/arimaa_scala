package controller.impl.command

trait CommandTrait {
  def doCommand(): Unit

  def undoCommand(): Unit

}
