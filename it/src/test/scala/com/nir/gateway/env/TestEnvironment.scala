package com.nir.gateway.env

import java.util.concurrent.{Executors, ScheduledExecutorService}

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import org.testcontainers.containers.Network
import cats.implicits._
import com.nir.gateway.client.ApiClient
import com.nir.gateway.http.HttpClient
import com.nir.gateway.http.models.HttpConfig
import com.nir.gateway.monitor.Logging
import com.nir.gateway.retries.models.RetryPolicy

import scala.concurrent.{ExecutionContext, Future}

case class HostPort(host: String, port: Int)

private[env] case class EnvData(shopify: HostPort)

class TestEnvironment extends Logging {

  implicit val system: ActorSystem = ActorSystem("server-it")
  implicit val mat: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher
  private implicit val scheduler: ScheduledExecutorService =
    Executors.newSingleThreadScheduledExecutor()

  private val network = Network.newNetwork()

  var apiClient: ApiClient = _

  private val imageBuilder = new ImageBuilder
  private val paymentGatewayMock = new PaymentGatewayFixture

  private val serverContainer = new ServerContainer(network)

  def start(): Future[Unit] = {
    val f = for {
      envData <- startCollaborators()
      hostPort <- serverContainer.start(envData)
      _ = apiClient = createApiClient(hostPort)
    } yield ()
    f.failed.foreach(t => logger.error(s"server-it startup failed with", t))
    f
  }

  def stop(): Future[Unit] =
    List(serverContainer.stop()).sequence.void

  private def startCollaborators() =
    (
      imageBuilder.buildImage("server"),
      paymentGatewayMock
        .start(),
    ).mapN {
      case (_, paymentGateway) =>
        EnvData(paymentGateway)
    }

  private def createApiClient(hostPort: HostPort) =
    new ApiClient(
      HttpClient.resource(
        HttpConfig(RetryPolicy(), s"http://${hostPort.host}:${hostPort.port}")
      )
    )

}
