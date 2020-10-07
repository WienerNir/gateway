package com.nir.store

import akka.actor.ActorSystem
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.{Eventually, ScalaFutures}
import org.scalatest.{
  EitherValues,
  FlatSpec,
  FlatSpecLike,
  Inside,
  Matchers,
  OptionValues,
  TryValues
}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

/**
  * This trait intentionally doesn't contain any specific testing flavor
  * The decision as to which testing flavor to use is up to the development team and can vary between teams
  */
trait UnitSpec
    extends Matchers
    with ScalaFutures
    with Inside
    with OptionValues
    with EitherValues
    with Eventually
    with MockFactory
    with FlatSpecLike {

  behavior of getClass.getSimpleName.stripSuffix("Spec")

  protected implicit val system: ActorSystem = ActorSystem()
  protected implicit val ec: ExecutionContext =
    ExecutionContext.Implicits.global

  protected implicit val patience: PatienceConfig =
    PatienceConfig(30.seconds, 2.second)
}
