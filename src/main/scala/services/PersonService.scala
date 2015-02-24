package services

import controller.SmartCarDemoController
import spray.http.StatusCodes
import spray.routing.HttpService

import scala.util.Random

trait PersonService extends HttpService {
  val addPersonRoute = path("addPerson") {
    (get | put) { ctx =>
      Random.nextBoolean() match {
        case true => SmartCarDemoController.addDriver()
        case false => SmartCarDemoController.addPassenger()
      }
      ctx.redirect("/", StatusCodes.SeeOther)
    }
  }
}
