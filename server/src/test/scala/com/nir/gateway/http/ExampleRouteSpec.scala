package com.nir.gateway.http

import akka.http.scaladsl.model.HttpRequest
import com.nir.gateway.UnitSpec
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.nir.gateway.gateway.ChargreRequest
import org.scalatest.concurrent.ScalaFutures
import com.nir.gateway.gateway.ChargreRequest._
import MediaTypes._
import io.circe.syntax._

class ExampleRouteSpec
    extends UnitSpec
    with ScalaFutures
    with ScalatestRouteTest {

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
