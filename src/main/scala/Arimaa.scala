import com.typesafe.scalalogging.Logger
import model.FieldTrait
import model.impl.Field

object Arimaa {
  val logger = Logger("Arimaa")

  def main(ags: Array[String]): Unit = {
    val field: FieldTrait = new Field()

    logger.info("Arimaa")
    logger.info(field.toString)

  }
}
