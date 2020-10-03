package com.nir.env

import cats.implicits._
import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.stream.ActorMaterializer
import com.nir.test.{EmbeddedServer, RandomPort}
import com.nir.test.EmbeddedServer.Handler

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}

class PaymentGatewayFixture(maybePort: Option[Int] = None)(
  implicit ex: ExecutionContext,
  system: ActorSystem,
  materializer: ActorMaterializer
) {

  private val port = maybePort.getOrElse(RandomPort.randomFreePort())

  val successes: ListBuffer[String] = ListBuffer.empty[String]

  private val chargebackHandler: Handler = {
    case request if request.uri.toString.endsWith("/visa/api/chargeCard") =>
      val body = Await.result(
        request.entity
          .toStrict(10.seconds)
          .map(_.getData())
          .map(_.utf8String),
        10.seconds
      )

      val origId = io.circe.parser
        .parse(body)
        .toOption
        .flatMap(_.asObject)
        .flatMap(o => o("id"))
        .flatMap(_.asString)

      origId match {
        case Some(s) if s.endsWith("-fail") =>
          HttpResponse(status = StatusCodes.InternalServerError)

        case Some(s) =>
          println(s"successfully handled $s, adding to successes")
          successes.append(s)
          HttpResponse(status = StatusCodes.OK)

        case None =>
          HttpResponse(status = StatusCodes.BadRequest)
      }
  }

  private var server: Option[EmbeddedServer] = None

  def start(): Future[HostPort] = {
    server = Some(new EmbeddedServer(port = port))
    server.foreach(_.addHandlers(chargebackHandler))
    server.foreach(_.start())
    println(
      s"PaymentGateway mock server is listening on http://localhost:$port"
    )
    Future.successful(HostPort("host.docker.internal", port))
  }

  def stop(): Future[Done] = {
    println("PaymentGateway mock server shutting down")
    server.foreach(_.close())
    Done.pure[Future]
  }

}
