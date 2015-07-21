package controller

import scroll.internal.graph.ScalaRoleGraph
import model.SmartCarDemo
import utils.TimeUtils

object SmartCarDemoController {
  def getRoleGraph: ScalaRoleGraph = SmartCarDemo.demo.transportation.plays

  def addNormalCar() {
    val car = new SmartCarDemo.demo.Car(TimeUtils.nowAsString)
    val normalCar = new SmartCarDemo.demo.transportation.ManualTransport.NormalCar()
    SmartCarDemo.demo.transportation.addPlaysRelation(car, normalCar)
  }

  def addSmartCar() {
    val car = new SmartCarDemo.demo.Car(TimeUtils.nowAsString)
    val normalCar = new SmartCarDemo.demo.transportation.AutonomousTransport.SmartCar()
    SmartCarDemo.demo.transportation.addPlaysRelation(car, normalCar)
  }

  def addDriver() {
    val person = new SmartCarDemo.demo.Person(TimeUtils.nowAsString)
    val driver = new SmartCarDemo.demo.transportation.ManualTransport.Driver()
    SmartCarDemo.demo.transportation.addPlaysRelation(person, driver)
  }

  def addPassenger() {
    val person = new SmartCarDemo.demo.Person(TimeUtils.nowAsString)
    val passenger = new SmartCarDemo.demo.transportation.AutonomousTransport.Passenger()
    SmartCarDemo.demo.transportation.addPlaysRelation(person, passenger)
  }
}
