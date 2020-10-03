package com.nir.gateway.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import com.nir.gateway.http.models.HttpConfig
import com.nir.gateway.retries.RetryClient
import retry.Success

import scala.concurrent.{ExecutionContext, Future}

class HttpClientImpl(httpConfig: HttpConfig)(implicit system: ActorSystem,
                                             ec: ExecutionContext)
    extends HttpClient {

  implicit val successHttpResponse =
    Success[HttpResponse](_.status != StatusCodes.InternalServerError)

  override def get(
    uriSuffix: String,
    headers: List[(String, String)] = Nil
  ): Future[HttpResponse] =
    makeRequest(uriSuffix, HttpMethods.GET, headers)

  override def post(uriSuffix: String,
                    headers: List[(String, String)] = Nil,
                    json: String = ""): Future[HttpResponse] =
    makeRequest(uriSuffix, HttpMethods.POST, headers, json)

  def makeRequest(uriSuffix: String,
                  method: HttpMethod,
                  headers: List[(String, String)] = Nil,
                  json: String = "") =
    RetryClient.backoff(
      httpConfig.retryPolicy,
      Http().singleRequest(
        HttpRequest(
          method = method,
          uri = httpConfig.baseUri + uriSuffix,
          entity = toJsonEntity(json),
          headers = prepareHeaders(headers)
        )
      )
    )

  private def toJsonEntity(json: String) =
    HttpEntity(ContentTypes.`application/json`, json)

  private def prepareHeaders(headers: List[(String, String)]) =
    headers.map(t => RawHeader(t._1, t._2))

}

object HttpClientImpl {

  def resource(httpConfig: HttpConfig)(implicit system: ActorSystem,
                                       ec: ExecutionContext) = {
    new HttpClientImpl(httpConfig)
  }
}
