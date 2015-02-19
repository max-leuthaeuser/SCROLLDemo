package services

import spray.routing.HttpService
import spray.routing.directives.CachingDirectives._

import scala.concurrent.duration.Duration

trait StaticContentService extends HttpService {
  val CACHE_MAX_CAPACITY = 1000
  val CACHE_DURATION = Duration("30 min")

  val simpleCache = routeCache(maxCapacity = CACHE_MAX_CAPACITY, timeToLive = CACHE_DURATION)

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
