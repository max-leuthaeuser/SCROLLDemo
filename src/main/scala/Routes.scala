import spray.routing.Directives._
import spray.http._
import MediaTypes._
import utils.TimeUtils

object Routes
{

  val indexRoute = path("") {
    get {
      respondWithMediaType(`text/html`) {
        complete {
          html.index().toString
        }
      }
    }
  }

}
