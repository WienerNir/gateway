package com.nir.gateway.env

import cats.implicits._
import com.nir.gateway.monitor.Logging
import org.slf4j.LoggerFactory
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.containers.{GenericContainer, Network}

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}
import scala.language.existentials

class ServerContainer(network: Network)(implicit ec: ExecutionContext)
    extends Logging {

  private val Port = 8888
  private val ServerImage = "server:0.1.0-SNAPSHOT"

  private type Container = X forSome { type X <: GenericContainer[X] }

  private lazy val logConsumer: Slf4jLogConsumer = new Slf4jLogConsumer(
    LoggerFactory.getLogger("server-it-container")
  )

  private lazy val container = new GenericContainer(ServerImage)
    .asInstanceOf[Container]
    .withExposedPorts(Port)
    .withNetwork(network)
    .waitingFor(Wait.forHttp("/healthcheck").forStatusCode(200))

  final def start(envData: EnvData): Future[HostPort] =
    for {
      env <- prepareEnv(envData).pure[Future]
      _ <- Future {
        container.setEnv(env.map(x => s"${x._1}=${x._2}").toList.asJava)
      }
      forPrint = env
        .map(kv => s"${kv._1} = ${kv._2}")
        .toList
        .sorted
        .mkString("\n")
      _ = println(s"Starting server with environment:\n$forPrint")
      _ <- Future { container.start() }
      _ = container.followOutput(logConsumer)
      hostPort = HostPort(
        container.getContainerIpAddress,
        container.getMappedPort(Port).toInt
      )
    } yield hostPort

  private def prepareEnv(envData: EnvData): Map[String, String] =
    Map("RUN_ENV" -> "it", "SERVER_PORT" -> Port.toString)

  def stop(): Future[Unit] = Future {
    container.stop()
  }

}
