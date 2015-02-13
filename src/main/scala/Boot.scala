import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http

/** Application main entry point. Create and run all relevant actors and bind spray services. */
object Boot extends App
{
  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[DemoServiceActor], "demo-service")

  // get our logging object
  val log = system.log

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = Config.IP, port = Config.PORT)

  /** Call this method on terminating the server for unbinding. */
  def terminate()
  {
    log.info("Terminating server ...")
    IO(Http) ! Http.Unbind
    log.info("Done. All services terminated normally.")
  }
}
