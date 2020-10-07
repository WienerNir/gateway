package com.nir.gateway.gateway

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpEntity
import akka.stream.Materializer
import com.nir.gateway.http.{BodyExtractor, HttpExtractor}

import scala.concurrent.ExecutionContext

class Test(extractor: HttpExtractor = BodyExtractor)(
  implicit executionContext: ExecutionContext,
  actorSystem: ActorSystem,
  mat: Materializer
) {

  def a(entity: HttpEntity) = {
    extractor.extract[ChargeResponse](entity)
  }
}
