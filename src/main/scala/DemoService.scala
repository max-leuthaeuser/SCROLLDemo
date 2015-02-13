import java.io.File

import akka.actor.{ActorContext, Actor}
import spray.routing._
import Routes._
import spray.routing.directives.CachingDirectives._
import scala.concurrent.duration.Duration

/** Holding the context actor system and the standard route for our service. */
class DemoServiceActor extends Actor
                                     with DemoService
{
  // we don't implement our route structure directly in the service actor because
  // we want to be able to test it independently, without having to spin up an actor

  /** The HttpService trait defines only one abstract member, which
    * connects the services environment to the enclosing actor or test
    */
  def actorRefFactory: ActorContext = context

  /** this actor only runs our route, but you could add
    * other things here, like request stream processing
    * or timeout handling.
    */
  def receive: Receive = runRoute(myRoute)
}

/** Defines our service behavior independently from the service actor. */
trait DemoService extends HttpService
{
  val CACHE_MAX_CAPACITY = 1000
  val CACHE_DURATION = Duration("30 min")

  val simpleCache = routeCache(maxCapacity = CACHE_MAX_CAPACITY, timeToLive = CACHE_DURATION)

  val myRoute = indexRoute ~
    path(Rest) {
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
