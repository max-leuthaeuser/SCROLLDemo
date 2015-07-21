import model.SmartCarDemo
import org.scalatest.{Matchers, GivenWhenThen, FeatureSpec}

class SmartCarDemoTest extends FeatureSpec with GivenWhenThen with Matchers {
  info("Test spec for demo app.")

  feature("Running examples") {
    scenario("SmartCarDemo") {
      When("Running the SmartCarDemo example")
      Then("There should be no error or exception.")
      val demo = SmartCarDemo.demo
      assert(null != demo.transportation)
      And("The role graph should not be empty.")
      demo.transportation.plays.store.vertexSet().isEmpty shouldBe false
    }
  }
}
