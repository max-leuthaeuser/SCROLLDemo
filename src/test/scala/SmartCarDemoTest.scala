import model.SmartCarDemo
import org.scalatest.{Matchers, GivenWhenThen, FeatureSpec}

class SmartCarDemoTest extends FeatureSpec with GivenWhenThen with Matchers {
  info("Test spec for demo app.")

  feature("Running examples") {
    scenario("SmartCarDemo") {
      When("Running the SmartCarDemo example")
      Then("There should be no error or exception.")
      assert(null != new SmartCarDemo().transportation)
    }

    scenario("SmartCarDemo role graph") {
      When("Running the SmartCarDemo example")
      Then("The role graph should not be empty")
      SmartCarDemo.demo.transportation.plays.store.isEmpty shouldBe false
    }
  }
}
