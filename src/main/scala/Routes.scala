import akka.actor.{ActorRefFactory, Actor}
import services.{StaticContentService, RoleGraphService, IndexService}

class RoutesActor extends Actor with Routes {
  override val actorRefFactory: ActorRefFactory = context

  def receive = runRoute(routes)
}

trait Routes extends IndexService
                with RoleGraphService
                with StaticContentService
{
  val routes = indexRoute ~ roleGraphRoute ~ staticContent
}