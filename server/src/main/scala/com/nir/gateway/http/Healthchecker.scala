package com.nir.gateway.http

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import com.nir.gateway.monitor.Logging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Route
import scala.concurrent.ExecutionContext

class Healthchecker(implicit executionContext: ExecutionContext,
                    actorSystem: ActorSystem,
                    mat: Materializer)
    extends Logging
    with FailFastCirceSupport {

  def routes: Route = healthcheck

  val healthcheck: Route =
    path("healthcheck") {
      get {
        complete(HttpResponse(StatusCodes.OK))
      }
    }
}
