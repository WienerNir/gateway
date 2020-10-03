package com.nir.gateway.gateway

import io.circe.Decoder
import io.circe.generic.semiauto._

case class ChargeResponse(chargeResult: String, resultReason: String)

object ChargeResponse {

  implicit val decoder: Decoder[ChargeResponse] = deriveDecoder

}
