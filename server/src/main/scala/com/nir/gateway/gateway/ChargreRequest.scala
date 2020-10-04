package com.nir.gateway.gateway

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import io.circe.syntax._

case class ChargreRequest(fullName: String = "name",
                          number: String = "4111111111111111",
                          expiration: String = "12/29",
                          cvv: String = "322",
                          totalAmount: BigDecimal = 23)

object ChargreRequest {

  implicit val encoder: Encoder[ChargreRequest] = deriveEncoder[ChargreRequest]
  implicit val decoder: Decoder[ChargreRequest] = deriveDecoder[ChargreRequest]

}
