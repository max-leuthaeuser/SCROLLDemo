package services

import spray.http.MediaTypes._
import spray.routing._

trait IndexService extends HttpService {

  val indexRoute = path("") {
    get {
      respondWithMediaType(`text/html`) {
        complete {
          html.index().toString()
        }
      }
    }
  }

}
