package com.nir.gateway.gateway

import scala.concurrent.Future

class Example {

  def handle(chargeRequest: ChargreRequest): Future[ChargeResponse] =
    Future.successful(ChargeResponse("a", "b"))

  def getExample(e: Color): Future[ChargeResponse] =
    Future.successful(ChargeResponse(e.toString, "b"))

}

object Example {

  def resource = new Example()
}
