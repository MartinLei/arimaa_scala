package controller.impl.command

trait CommandTrait {
  def doCommand(): String

  def undoCommand(): String

}
