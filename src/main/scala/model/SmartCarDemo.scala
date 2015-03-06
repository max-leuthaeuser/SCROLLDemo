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

    +toyota drive()
    ManualTransport play new TransportationRole(one[Source]("name" ==# "Berlin"), one[Target]()) travel()

    // and here a autonomous one
    peter play new AutonomousTransport.Passenger()
    googleCar play new AutonomousTransport.SmartCar()
    AutonomousTransport partOf this

    +googleCar drive()
    AutonomousTransport play new TransportationRole(one[Source]("name" ==# "Munich"), one[Target]()) travel()

    +peter break()
    +harry break()
  }

  /**
   * Defining all natural types.
   */

  class Person(val name: String)

  class Car(val licenseID: String)

  class Location(val name: String)

  /**
   * Defining all contexts with it's roles.
   */

  class Charging() extends Compartment {

    @Role class ChargingStation() {
      def doCharge() {
        info("Charging ...")
      }
    }

    @Role class ChargingCar() {
      def drive() {
        info("I can't drive while charging!")
      }
    }

  }

  class Transportation() extends Compartment {

    object AutonomousTransport extends Compartment {

      @Role class SmartCar() {
        def drive() {
          info("I am driving autonomously!")
        }
      }

      @Role class Passenger() {
        def break() {
          val name: String = (+this).name
          info(s"I can't reach the break. I am $name and just a passenger!")
        }
      }

    }

    object ManualTransport extends Compartment {

      @Role class NormalCar() {
        def drive() {
          val driver = one[Driver]()
          info("I am driving with a driver called " + (+driver).name + ".")
        }
      }

      @Role class Driver() {
        def break() {
          val name: String = (+this).name
          info(s"I am $name and I am hitting the brakes now!")
        }
      }

    }

    @Role class TransportationRole(source: Source, target: Target) {
      def travel() {
        val from: String = (+source).name
        val to: String = (+target).name
        val license: String = one[Car]().licenseID

        val kindOfTransport = this player match {
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