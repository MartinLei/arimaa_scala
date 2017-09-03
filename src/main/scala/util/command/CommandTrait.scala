package util.command


trait CommandTrait {
  def doCommand(): Unit

  def undoCommand(): Unit

}
