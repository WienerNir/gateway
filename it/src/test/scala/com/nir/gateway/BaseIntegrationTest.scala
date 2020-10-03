package com.nir.gateway

import com.nir.gateway.env.TestEnvironment
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.{EitherValues, FlatSpec, Matchers, OptionValues}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

class BaseIntegrationTest(protected val env: TestEnvironment)
    extends FlatSpec
    with ScalaFutures
    with Matchers
    with Eventually
    with EitherValues
    with OptionValues {

  override implicit val patienceConfig: PatienceConfig =
    PatienceConfig(timeout = 1.minute, interval = 100.millis)

  protected implicit val ec: ExecutionContext = env.ec

}
