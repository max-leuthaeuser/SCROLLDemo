package services

import controller.SmartCarDemoController
import spray.http.StatusCodes
import spray.routing.HttpService

import scala.util.Random

trait CarService extends HttpService {
  val addCarRoute = path("addCar") {
    (get | put) { ctx =>
      Random.nextBoolean() match {
        case true => SmartCarDemoController.addNormalCar()
        case false => SmartCarDemoController.addSmartCar()
      }
      ctx.redirect("/", StatusCodes.SeeOther)
    }
  }
}
