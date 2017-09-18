package controller.impl.command

import model.impl.PlayerNameEnum.PlayerNameEnum

import scala.collection.mutable.ListBuffer

case class PlayerTurn(name: PlayerNameEnum) {
  private var actionList: ListBuffer[ActionCommand] = ListBuffer()

  def doAction(action: ActionCommand): List[String] = {
    actionList.+=(action)
    action.doAction()
  }
  def undoAction: List[String] ={

  }
}
