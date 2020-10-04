package com.nir.gateway.gateway

import scala.concurrent.Future

class Example {

  def handle(chargeRequest: ChargreRequest): Future[ChargeResponse] =
    Future.successful(ChargeResponse("a", "b"))

}

object Example {

  def resource = new Example()
}
