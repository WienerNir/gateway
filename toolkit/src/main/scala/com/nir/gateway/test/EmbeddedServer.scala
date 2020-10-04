package com.nir.gateway.test

import java.util.concurrent.atomic.AtomicInteger

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{
  HttpMessage,
  HttpRequest,
  HttpResponse,
  StatusCodes
}
import akka.stream.Materializer
import com.nir.gateway.tap.TapSyntax._
import com.nir.gateway.monitor.Logging
import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

class EmbeddedServer(host: String = "localhost", port: Int)(
  implicit val system: ActorSystem,
  val mat: Materializer
) extends AutoCloseable
    with Logging {

  import EmbeddedServer._

  private implicit val ec: ExecutionContext = system.dispatcher

  private val sequenceNumber = new AtomicInteger()
  private var maybeServerBinding: Option[Future[Http.ServerBinding]] = None
  private val requestHandlers: ListBuffer[Handler] = ListBuffer.empty

  private val FallbackHandler: Handler = {
    case r: HttpRequest =>
      r.discardEntityBytes()
      HttpResponse(StatusCodes.NotFound)
  }

  def start(): Unit = {
    magenta(s"EmbeddedServer listening on $host:$port")
    maybeServerBinding = Option(
      Http().bindAndHandleSync(handleRequest, host, port)
    )
  }

  def close(): Unit =
    maybeServerBinding.foreach { binding =>
      binding
        .flatMap(_.unbind())
        .onComplete(_ => system.terminate())
    }

  def reset(): Unit = requestHandlers.clear()

  def addHandlers(handlers: Handler*): Unit =
    requestHandlers.append(handlers: _*)

  private def handleRequest: Handler = {
    case request =>
      val sn = sequenceNumber.getAndIncrement()
      logMessage(request, sn)
      combinedHandler
        .apply(request)
        .tap(response => logMessage(response, sn))
  }

  private def combinedHandler: Handler =
    (requestHandlers :+ FallbackHandler).reduceLeft(_ orElse _)

  private def logMessage(httpMessage: HttpMessage, sn: Int): Unit =
    magenta(s"EmbeddedServer($port) #$sn ${if (httpMessage.isRequest()) "<<<"
    else ">>>"}\n$httpMessage")

  private def magenta(s: String): Unit =
    logger.info(s"${Console.MAGENTA}$s${Console.RESET}")

}

object EmbeddedServer {

  type Handler = PartialFunction[HttpRequest, HttpResponse]

}
