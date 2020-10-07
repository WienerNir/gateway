package com.nir.gateway.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives.{
  complete,
  get,
  handleWith,
  parameters,
  path,
  post,
  _
}
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.nir.gateway.gateway.{Color, Example}
import com.nir.gateway.monitor.Logging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.ExecutionContext

class ExampleRoute()(implicit executionContext: ExecutionContext,
                     actorSystem: ActorSystem,
                     mat: Materializer)
    extends Logging
    with FailFastCirceSupport {

  def routes: Route = exampleRoute ~ getExampleRoute

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
