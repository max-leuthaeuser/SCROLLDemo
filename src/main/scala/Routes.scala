import akka.actor.{ActorRefFactory, Actor}
import services._

class RoutesActor extends Actor with Routes {
  override val actorRefFactory: ActorRefFactory = context

  def receive = runRoute(routes)
}

trait Routes extends IndexService
                with RoleGraphService
                with StaticContentService
                with CarService
                with PersonService
{
  val routes =  indexRoute ~
                roleGraphRoute ~
                addCarRoute ~
                addPersonRoute ~
                staticContent
}