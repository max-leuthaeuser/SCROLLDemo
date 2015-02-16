package controller

import internal.graph.ScalaRoleGraph
import model.SmartCarDemo

class SmartCarDemoController {
  def getRoleGraph: ScalaRoleGraph = SmartCarDemo.demo.transportation.plays
}
