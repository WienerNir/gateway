package com.nir.gateway.gateway

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpEntity
import com.nir.gateway.UnitSpec
import com.nir.gateway.http.HttpExtractor
import io.circe.Decoder

import scala.concurrent.{ExecutionContext, Future}

class TestSpec extends UnitSpec {

  val extr = stub[HttpExtractor]

  val subject = new Test(extr)

  "a" should {
    "verift body" in {
      (extr
        .extract[ChargeResponse](_: HttpEntity)(
          _: ExecutionContext,
          _: Decoder[ChargeResponse],
          _: ActorSystem
        ))
        .when(*, *, *, *)
        .returns(Future.successful(ChargeResponse("a", "a")))
      subject.a(HttpEntity("entity")).futureValue.chargeResult shouldEqual "a"
    }
  }

}
