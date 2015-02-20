package model

import annotations.Role
import internal.Compartment
import internal.util.Log.info

import scala.language.reflectiveCalls

/**
 * Demo application of smart cars driving around.
 * Either autonomously transporting passengers or the normal way with a driver.
 * Sometimes the car needs to be charged though.
 *
 * TODO:
 *
 * - add more runtime examples at instance level for transporting and charging where
 * behavior gets exchanged based on role-based dispatch.
 */
class SmartCarDemo {

  val transportation = new Transportation {

    val peter = new Person("Peter")
    val car = new Car("A-B-C-001")

    peter play new Driver()
    car play new NormalCar()

    +car drive()
    +peter break()
  }

  /**
   * Defining all natural types.
   */

  class Person(name: String) {
    def getName: String = name
  }

  class Car(licenseID: String) {
    def getLicenseID: String = licenseID

    def drive(): Unit = {
      info("I am driving.")
    }
  }

  class Location(name: String) {
    def getName: String = name
  }

  /**
   * Defining all contexts with it's roles.
   */

  class Charging() extends Compartment {

    @Role class ChargingStation() {
      def doCharge(): Unit = {
        info("Charging ...")
      }
    }

    @Role class ChargingCar() {
      def drive(): Unit = {
        info("I can't drive while charging!")
      }
    }

  }

  class Transportation() extends Compartment {

    @Role class SmartCar() {
      def drive(): Unit = {
        info("I am driving autonomously!")
      }
    }

    @Role class NormalCar() {
      def drive(): Unit = {
        info("I am driving with a driver!")
      }
    }

    @Role class Passenger() {
      def break(): Unit = {
        info("I can't reach the break. I am just a passenger!")
      }
    }

    @Role class Driver() {
      def break(): Unit = {
        info("I do break!")
      }
    }

    @Role class TransportationRole()

    @Role class Target()

    @Role class Source()

  }

}

object SmartCarDemo {
  lazy val demo = new SmartCarDemo()
}