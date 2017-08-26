package controller.impl

import controller.ControllerTrait
import model.FieldTrait
import model.impl.Field

class Controller extends ControllerTrait {
  private val field: FieldTrait = new Field()

  override def getFieldAsString: String = {
    field.toString
  }
}
