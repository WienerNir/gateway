package com.nir.gateway.http

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.nir.gateway.gateway.Example
import com.nir.gateway.monitor.Logging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import scala.concurrent.ExecutionContext

class Healthchecker(httpClient: HttpClientImpl)(
  implicit executionContext: ExecutionContext,
  actorSystem: ActorSystem,
  mat: Materializer
) extends Logging
    with FailFastCirceSupport {

  def routes: Route = healthcheck ~ exampleRoute

  val healthcheck: Route =
    path("healthcheck") {
      get {
        complete(HttpResponse(StatusCodes.OK))
      }
    }

//  example for handle with
  private val exampleRoute: Route =
    path("example") {
      (post) {
        handleWith(Example.resource.handle)
      }
    }
}