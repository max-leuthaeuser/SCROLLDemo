import annotations.Role
import internal.Context

/**
 * Demo application of smart cars driving around.
 * Either autonomously transporting passengers or the normal way with a driver.
 * Sometimes the car needs to be charged though.
 *
 * TODO:
 *
 * - add behavior
 * - add runtime example at instance level for transporting and charging where
 * behavior gets exchanged based on role-based dispatch.
 */
object SmartCarDemo extends App {

  /**
   * Defining all natural types.
   */

  class Person()

  class Car()

  class Location()

  /**
   * Defining all contexts with it's roles.
   */

  class Charging() extends Context {

    @Role class ChargingStation()

    @Role class Charging()

  }

  class Transportation() extends Context {

    @Role class SmartCar()

    @Role class NormalCar()

    @Role class Passenger()

    @Role class Driver()

    @Role class TransportationRole()

    @Role class Target()

    @Role class Source()

  }

}