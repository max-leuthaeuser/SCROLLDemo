import controller.SmartCarDemoController
import internal.graph.ScalaRoleGraph
import spray.http.MediaTypes._
import spray.json._
import spray.routing.Directives._

object Routes {

  object ScalaRoleGraphJsonProtocol extends DefaultJsonProtocol {

    implicit object ScalaRoleGraphJsonFormat extends RootJsonFormat[ScalaRoleGraph] {
      def write(c: ScalaRoleGraph) = {
        val content = JsArray(c.store.nodes.toVector.filter(n => n.outDegree > 0).map(n => {
          val node = n.value.getClass.getSimpleName
          val hash = node.hashCode
          JsObject(
            "name" -> s"$node (#$hash)".toJson,
            "children" -> JsArray(n.outgoing.toVector.map(n => {
              val out = n.target.value.getClass.getSimpleName
              val outHash = out.hashCode
              JsObject("name" -> s"$out (#$outHash)".toJson)
            }))
          )
        }))

        val root = JsObject("name" -> "SmartCarDemo".toJson, "children" -> content)
        root
      }

      def read(value: JsValue) = ??? // not needed
    }

  }

  val indexRoute = path("") {
    get {
      respondWithMediaType(`text/html`) {
        complete {
          html.index().toString()
        }
      }
    }
  }

  import ScalaRoleGraphJsonProtocol._

  val roleGraphRoute = path("rolegraph.json") {
    get {
      respondWithMediaType(`application/json`) {
        complete {
          new SmartCarDemoController().getRoleGraph.toJson.prettyPrint
        }
      }
    }
  }
}
