package com.nir.gateway.app

import com.nir.gateway.http.{CommonDirectives, Healthchecker, HttpClientImpl}
import pureconfig.ConfigSource
import akka.http.scaladsl.server.Route
import scala.concurrent.ExecutionContext
import pureconfig.generic.auto._

object ServerFactory {

  def create(implicit actorSystem: akka.actor.ActorSystem,
             ec: ExecutionContext): Route = {

    val config = ConfigSource.default.loadOrThrow[ServerConfig]

    CommonDirectives.routeRoot(
      new Healthchecker(HttpClientImpl.resource(config.http)).routes
    )
  }
}