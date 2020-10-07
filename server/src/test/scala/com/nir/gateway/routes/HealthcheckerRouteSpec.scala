package com.nir.gateway.routes

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.model.{HttpRequest, _}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.nir.gateway.UnitSpec
import org.scalatest.concurrent.ScalaFutures

class HealthcheckerRouteSpec
    extends UnitSpec
    with ScalaFutures
    with ScalatestRouteTest {

  lazy val testKit = ActorTestKit()
  implicit def typedSystem = testKit.system
  implicit val classicSystem: akka.actor.ActorSystem =
    testKit.system.classicSystem

  lazy val routes = new HealthcheckerRoute().routes

  "HealthcheckRoutes" should {
    "return 200 for get request" in {
      val request = HttpRequest(uri = "/healthcheck")

      request ~> routes ~> check {
        status shouldEqual StatusCodes.OK
      }
    }
  }

}
