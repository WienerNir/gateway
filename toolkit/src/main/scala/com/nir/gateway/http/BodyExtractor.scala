package com.nir.gateway.http

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpEntity
import cats.ApplicativeError
import io.circe.Decoder
import io.circe.parser.decode
import cats.ApplicativeError
import cats.implicits._
import com.nir.gateway.monitor.Logging

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

object BodyExtractor extends HttpExtractor {

  private val ResponseReadTimeout = 5.seconds

  def extract[T](entity: HttpEntity)(implicit ec: ExecutionContext,
                                     decoder: Decoder[T],
                                     system: ActorSystem): Future[T] =
    entity
      .toStrict(ResponseReadTimeout)
      .map(_.getData())
      .map(_.utf8String)
      .map(decode[T](_))
      .flatMap(ApplicativeError[Future, Throwable].fromEither)
}
