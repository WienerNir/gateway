package com.nir.gateway.http

import akka.http.scaladsl.model.HttpRequest
import com.nir.gateway.UnitSpec
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures

class HealthcheckerSpec
    extends UnitSpec
    with ScalaFutures
    with ScalatestRouteTest {

  lazy val testKit = ActorTestKit()
  implicit def typedSystem = testKit.system
  implicit val classicSystem: akka.actor.ActorSystem =
    testKit.system.classicSystem

  lazy val routes = new Healthchecker().routes

  "HealthcheckRoutes" should {
    "return 200 for get request" in {
      val request = HttpRequest(uri = "/healthcheck")

      request ~> routes ~> check {
        status shouldEqual StatusCodes.OK
      }
    }
  }

}
