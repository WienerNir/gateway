package com.nir.gateway.test

import java.net.{ConnectException, Socket, SocketException}

import scala.util.{Failure, Random, Success, Try}

object RandomPort {
  private final val MIN_PORT = 10000
  private final val MAX_PORT = 65356

  def randomFreePort(): Int = {
    val port = randomInt(MIN_PORT, MAX_PORT)
    if (isPortFree(port))
      port
    else
      throw new Exception("Could not find free port")
  }

  private def isPortFree(port: Int): Boolean =
    Try(new Socket("127.0.0.1", port).close()) match {
      case Success(_) =>
        false
      case Failure(_: ConnectException) =>
        true
      case Failure(e: SocketException)
          if e.getMessage == "Connection reset by peer" =>
        true
    }

  private def randomInt(min: Int, max: Int): Int =
    min + Random.nextInt((max - min) + 1)
}
