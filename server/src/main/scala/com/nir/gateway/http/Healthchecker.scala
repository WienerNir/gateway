package com.nir.gateway.http

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import com.nir.gateway.gateway.{Color, Example}
import com.nir.gateway.monitor.Logging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller

import scala.concurrent.ExecutionContext
import scala.util.Try

class Healthchecker(httpClient: HttpClientImpl)(
  implicit executionContext: ExecutionContext,
  actorSystem: ActorSystem,
  mat: Materializer
) extends Logging
    with FailFastCirceSupport {

  def routes: Route = healthcheck ~ exampleRoute ~ getExampleRoute

  val healthcheck: Route =
    path("healthcheck") {
      get {
        complete(HttpResponse(StatusCodes.OK))
      }
    }

//  example for handle with
  private val exampleRoute: Route =
    path("example") {
      post {
        handleWith(Example.resource.handle)
      }
    }

  private val getExampleRoute: Route =
    path("example") {
      (get & parameters('r.as[Int]).as(Color)) { v =>
        complete(Example.resource.getExample(v))
      }
    }
}
