package com.nir.gateway.routes

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.model._
import com.nir.gateway.UnitSpec
import com.nir.gateway.gateway.ChargreRequest
import com.nir.gateway.gateway.ChargreRequest._
import io.circe.syntax._

class ExampleRouteSpec extends UnitSpec {

  lazy val testKit = ActorTestKit()
  implicit def typedSystem = testKit.system
  implicit val classicSystem: akka.actor.ActorSystem =
    testKit.system.classicSystem

  lazy val routes = new ExampleRoute().routes

  "ExampleRoutes" should {

    "return 200 for ge,l;,lt request" in {
      val body = ChargreRequest()
      val request =
        Post(
          "/example",
          HttpEntity(ContentTypes.`application/json`, body.asJson.noSpaces)
        )

      request ~> routes ~> check {
        status shouldEqual StatusCodes.OK
        entityAs[String] shouldEqual """{"chargeResult":"a","resultReason":"b"}"""

      }
    }

    "return 200 for geffgdaggf,l;,lt request" in {
      val body = ChargreRequest()
      val request =
        Post(
          "/example",
          HttpEntity(
            ContentTypes.`application/json`,
            """{"chargeResult":"a","resultReason":"b"}"""
          )
        )

      request ~> routes ~> check {
        handled shouldBe false
      }
    }

  }

}
