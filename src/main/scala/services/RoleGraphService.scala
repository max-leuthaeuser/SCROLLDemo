package services

import controller.SmartCarDemoController
import scroll.internal.ReflectiveHelper
import scroll.internal.graph.ScalaRoleGraph
import spray.http.MediaTypes._
import spray.json._
import spray.routing.HttpService

trait RoleGraphService extends HttpService {

  object ScalaRoleGraphJsonProtocol extends DefaultJsonProtocol {

    private def getHashString(a: Any) = {
      val s = a.toString
      s.substring(s.indexOf("@") + 1, s.length)
    }

    private def children(c: ScalaRoleGraph, n: Any): JsArray = {
      val l = c.getRoles(n)
      JsArray(l.tail.toVector.map(cr => {
        val out = ReflectiveHelper.classSimpleClassName(cr.getClass.toString)
        val outHash = getHashString(cr)
        JsObject("name" -> s"$out (#$outHash)".toJson, "children" -> children(c, cr))
      }))
    }

    implicit object ScalaRoleGraphJsonFormat extends RootJsonFormat[ScalaRoleGraph] {
      def write(c: ScalaRoleGraph) = {
        val content = JsArray(c.allPlayers.toVector.filter(n => {
          val l = c.getRoles(n)
          l.nonEmpty && l.diff(Set(n)).nonEmpty
        }).map(n => {
          val node = ReflectiveHelper.classSimpleClassName(n.getClass.toString)
          val hash = getHashString(n)
          JsObject(
            "name" -> s"$node  (#$hash)".toJson,
            "children" -> children(c, n)
          )
        }))

        JsObject("name" -> "SmartCarDemo".toJson, "children" -> content)
      }

      def read(value: JsValue) = ??? // not needed
    }

  }


  import ScalaRoleGraphJsonProtocol._

  val roleGraphRoute = path("rolegraph.json") {
    get {
      respondWithMediaType(`application/json`) {
        complete {
          SmartCarDemoController.getRoleGraph.toJson.compactPrint
        }
      }
    }
  }
}
