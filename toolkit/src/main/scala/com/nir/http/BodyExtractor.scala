package com.nir.http

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpEntity
import cats.ApplicativeError
import io.circe.Decoder
import io.circe.parser.decode
import cats.ApplicativeError
import cats.implicits._
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import com.nir.monitor.Logging

sealed trait ApiResponse

object ApiResponse {
  case class SuccessResponse[T](payload: T) extends ApiResponse
  case class FailureResponse(error: String) extends ApiResponse
}

object BodyExtractor extends Logging {

  private val ResponseReadTimeout = 5.seconds

  def extractEntityBody[T](entity: HttpEntity)(implicit ec: ExecutionContext,
                                               decoder: Decoder[T],
                                               system: ActorSystem): Future[T] =
    entity
      .toStrict(ResponseReadTimeout)
      .map(_.getData())
      .map(_.utf8String)
      .map(decode[T](_))
      .flatMap(ApplicativeError[Future, Throwable].fromEither)
}
