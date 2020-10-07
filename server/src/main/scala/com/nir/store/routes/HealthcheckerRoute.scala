package com.nir.store.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.nir.store.monitor.Logging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.ExecutionContext

class HealthcheckerRoute(implicit executionContext: ExecutionContext,
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

object HealthcheckerRoute {

  def resource(implicit executionContext: ExecutionContext,
               actorSystem: ActorSystem,
               mat: Materializer) =
    new HealthcheckerRoute()

}
