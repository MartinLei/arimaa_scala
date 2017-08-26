import com.typesafe.scalalogging.Logger
import model.Field

object Arimaa {
  val logger = Logger("Arimaa")

  def main(ags: Array[String]): Unit = {
    val field: Field = new Field()

    logger.info("Arimaa")
    logger.info(field.toString)

  }
}
