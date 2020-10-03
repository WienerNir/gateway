package com.nir

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.nir.http.HttpClient
import com.nir.monitor.Logging
import io.circe.syntax._

import scala.concurrent.ExecutionContext

class Healthchecker(httpClient: HttpClient)(
  implicit executionContext: ExecutionContext,
  actorSystem: ActorSystem,
  mat: Materializer
) extends Logging {

  val route: Route =
    path("healthcheck") {
      get {
        logger.info("in healthcheck")
        complete(
          HttpResponse(
            StatusCodes.OK,
            entity = HealthCheckResponse("ok").asJson.toString()
          )
        )
      }
    }
}
