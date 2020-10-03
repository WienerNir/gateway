package com.nir.gateway.env

import com.nir.gateway.monitor.Logging

import scala.concurrent.{ExecutionContext, Future}
import scala.sys.process._

class ImageBuilder(implicit ec: ExecutionContext) extends Logging {

  private val processLogger: ProcessLogger = new ProcessLogger {
    override def out(s: => String): Unit = logger.info(s)
    override def err(s: => String): Unit = logger.error(s)
    override def buffer[T](f: => T): T = f
  }

  def buildImage(module: String): Future[Unit] = Future {
    println(s"Building and publishing docker image from $module")
    s"sbt $module/docker:publishLocal".run(processLogger)
    ()
  }

}
