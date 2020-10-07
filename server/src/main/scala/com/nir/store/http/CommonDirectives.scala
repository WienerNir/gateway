package com.nir.store.http

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{
  complete,
  handleExceptions,
  pathPrefix
}
import akka.http.scaladsl.server.{Directive0, ExceptionHandler}
import com.nir.store.monitor.Logging

object CommonDirectives extends Logging {

  private val exceptionHandler: ExceptionHandler = ExceptionHandler {
    case cause =>
      logger.error(s"HTTP request failed: ${cause.getMessage}", cause)
      complete(StatusCodes.InternalServerError)
  }

  val routeRoot: Directive0 = {
    handleExceptions(exceptionHandler)
  }

}
