package services

import spray.http.MediaTypes._
import spray.routing.{RejectionHandler, HttpService}
import spray.routing.directives.CachingDirectives._

import scala.concurrent.duration.Duration

trait StaticContentService extends HttpService {
  val CACHE_MAX_CAPACITY = 1000
  val CACHE_DURATION = Duration("10 min")

  val simpleCache = routeCache(maxCapacity = CACHE_MAX_CAPACITY, timeToLive = CACHE_DURATION)

  implicit val rejectionHandler: RejectionHandler = RejectionHandler {
    case Nil => respondWithMediaType(`text/html`) {
      complete {
        html.error().toString()
      }
    }
  }

  val staticContent = path(Rest) {
    // serving static files for bootstrap, cached and gzip compressed
    path =>
      get {
        cache(simpleCache) {
          compressResponse() {
            getFromResource("bootstrap/%s" format path)
          }
        }
      }
  }
}
