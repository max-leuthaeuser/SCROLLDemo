package services

import controller.SmartCarDemoController
import internal.ReflectiveHelper
import internal.graph.ScalaRoleGraph
import internal.graph.ScalaRoleGraph.Relation
import spray.http.MediaTypes._
import spray.json._
import spray.routing.HttpService

trait RoleGraphService extends HttpService {

  object ScalaRoleGraphJsonProtocol extends DefaultJsonProtocol {

    private def getHashString(a: Any) = {
      val s = a.toString
      s.substring(s.indexOf("@") + 1, s.length)
    }

    private def children(n: scalax.collection.mutable.Graph[Any, Relation]#NodeT): JsArray = JsArray(n.outgoing.toVector.map(n => {
      val out = ReflectiveHelper.classSimpleClassName(n.target.value.getClass)
      val outHash = getHashString(n.target.value)
      JsObject("name" -> s"$out (#$outHash)".toJson, "children" -> children(n.target))
    }))

    implicit object ScalaRoleGraphJsonFormat extends RootJsonFormat[ScalaRoleGraph] {
      def write(c: ScalaRoleGraph) = {
        val content = JsArray(c.store.nodes.toVector.filter(n => n.outDegree > 0).map(n => {
          val node = ReflectiveHelper.classSimpleClassName(n.value.getClass)
          val hash = getHashString(n.value)
          JsObject(
            "name" -> s"$node  (#$hash)".toJson,
            "children" -> children(n)
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
