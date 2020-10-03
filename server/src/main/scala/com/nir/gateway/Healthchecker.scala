package com.nir.gateway

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.nir.gateway.http.HttpClientImpl
import com.nir.gateway.monitor.Logging
import io.circe.syntax._

import scala.concurrent.ExecutionContext

class Healthchecker(httpClient: HttpClientImpl)(
  implicit executionContext: ExecutionContext,
  actorSystem: ActorSystem,
  mat: Materializer
) extends Logging {

  val route: Route =
    path("healthcheck") {
      get {
        complete(
          HttpResponse(
            StatusCodes.OK,
            entity = HealthCheckResponse("ok").asJson.toString()
          )
        )
      }
    }
}