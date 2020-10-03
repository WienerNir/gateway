package com.nir.gateway

import com.nir.gateway.env.TestEnvironment
import org.scalatest._

import scala.concurrent.Await
import scala.concurrent.duration._

class ServerIT extends FlatSpec with Matchers with BeforeAndAfterAll {

  private val env = new TestEnvironment

  override val nestedSuites: collection.immutable.IndexedSeq[Suite] =
    Suites(new HealthCheckEndpointIT(env)).nestedSuites

  override def beforeAll(): Unit = {
    Await.ready(env.start(), 5.minutes)
    ()
  }

  override def afterAll(): Unit = {
    Await.ready(env.stop(), 2.minutes)
    ()
  }

}
