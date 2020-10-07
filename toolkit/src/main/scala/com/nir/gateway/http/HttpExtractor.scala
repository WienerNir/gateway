package com.nir.gateway.http

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpEntity
import io.circe.Decoder

import scala.concurrent.{ExecutionContext, Future}

trait HttpExtractor {

  def extract[T](entity: HttpEntity)(implicit ec: ExecutionContext,
                                     decoder: Decoder[T],
                                     system: ActorSystem): Future[T]

}
