package com.nir.gateway

import io.circe.Encoder
import io.circe.generic.semiauto._

case class HealthCheckResponse(message: String)

object HealthCheckResponse {

  implicit val healthCheckResponseEncoder: Encoder[HealthCheckResponse] =
    deriveEncoder

}
