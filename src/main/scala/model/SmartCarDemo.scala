package model

import annotations.Role
import internal.Compartment
import internal.util.Log.info

import scala.language.reflectiveCalls
import scala.language.postfixOps

/**
 * Demo application of smart cars driving around.
 * Either autonomously transporting passengers or the normal way with a driver.
 * Sometimes the car needs to be charged though.
 *
 * TODO:
 *
 * - add associations (e.g. between car and driver etc.)
 */
class SmartCarDemo {

  val transportation = new Transportation {

    // adding some persons with their cars
    val peter = new Person("Peter")
    val googleCar = new Car("A-B-C-001")

    val harry = new Person("Harry")
    val toyota = new Car("A-B-C-002")

    // adding some locations
    new Location("Munich") play new Source()
    new Location("Berlin") play new Source()
    new Location("Dresden") play new Target()

    // and doing a manual transportation now
    harry play new ManualTransport.Driver()
    toyota play new ManualTransport.NormalCar()
    ManualTransport partOf this

    ManualTransport play new TransportationRole(one[Source]("getName" ==> "Berlin"), one[Target]()) travel()

    // and here a autonomous one
    peter play new AutonomousTransport.Passenger()
    googleCar play new AutonomousTransport.SmartCar()
    AutonomousTransport partOf this

    AutonomousTransport play new TransportationRole(one[Source]("getName" ==> "Munich"), one[Target]()) travel()

    // calling basic functionality
    +googleCar drive()
    +toyota drive()
    +peter break()
    +harry break()
  }

  /**
   * Defining all natural types.
   */

  class Person(name: String) {
    def getName(): String = name
  }

  class Car(licenseID: String) {
    def getLicenseID(): String = licenseID

    def drive(): Unit = {
      info("I am driving.")
    }
  }

  class Location(name: String) {
    def getName(): String = name
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

    object AutonomousTransport extends Compartment {

      @Role class SmartCar() {
        def drive(): Unit = {
          info("I am driving autonomously!")
        }
      }

      @Role class Passenger() {
        def break(): Unit = {
          info("I can't reach the break. I am just a passenger!")
        }
      }

    }

    object ManualTransport extends Compartment {

      @Role class NormalCar() {
        def drive(): Unit = {
          val driver = one[Driver]()
          info("I am driving with a driver called " + (-driver).getName() + ".")
        }
      }

      @Role class Driver() {
        def break(): Unit = {
          info("I do break!")
        }
      }

    }

    @Role class TransportationRole(source: Source, target: Target) {
      def travel() {
        val from: String = -source getName()
        val to: String = -target getName()
        val license: String = one[Car]().getLicenseID()
        
        val kindOfTransport = getCoreFor(this) match {
          case ManualTransport => "manual"
          case AutonomousTransport => "autonomous"
        }
        info(s"Doing a $kindOfTransport transportation with the car $license from $from to $to.")
      }

    }

    @Role class Target()

    @Role class Source()

  }

}

object SmartCarDemo {
  lazy val demo = new SmartCarDemo()
}