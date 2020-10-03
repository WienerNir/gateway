package com.nir.gateway.client

import akka.actor.ActorSystem
import akka.http.scaladsl.model._
import cats.ApplicativeError
import cats.implicits._
import com.nir.gateway.http.{HttpClient, HttpClientImpl}
import io.circe.Json
import io.circe.parser.parse

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

class ApiClient(httpClient: HttpClient)(implicit ec: ExecutionContext,
                                        system: ActorSystem) {

  def get[T](uriSuffix: String, headers: List[(String, String)] = Nil) =
    httpClient.get(uriSuffix, headers)

  def extractBody(response: HttpResponse): Future[Json] =
    response.entity
      .toStrict(10.seconds)
      .map(_.data.utf8String)
      .map(parse)
      .flatMap(ApplicativeError[Future, Throwable].fromEither)

}
