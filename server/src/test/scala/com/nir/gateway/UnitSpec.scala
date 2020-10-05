package com.nir.gateway

import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest._
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * This trait intentionally doesn't contain any specific testing flavor
  * The decision as to which testing flavor to use is up to the development team and can vary between teams
  */
trait UnitSpec
    extends WordSpec
    with MockFactory
    with ScalaFutures
    with Inside
    with OptionValues
    with EitherValues
    with Eventually
    with Matchers {

  protected implicit val patience: PatienceConfig =
    PatienceConfig(30.seconds, 2.second)
}
