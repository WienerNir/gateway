package com.nir.gateway.app

import com.nir.gateway.http.{
  CommonDirectives,
  ExampleRoute,
  Healthchecker,
  HttpClientImpl
}
import pureconfig.ConfigSource
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext
import pureconfig.generic.auto._

object ServerFactory {

  def create(implicit actorSystem: akka.actor.ActorSystem,
             ec: ExecutionContext): Route = {

    val config = ConfigSource.default.loadOrThrow[ServerConfig]
    CommonDirectives.routeRoot(new ExampleRoute().routes) ~ new Healthchecker().routes
  }
}
