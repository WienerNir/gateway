package com.nir

import io.circe.Encoder
import io.circe.generic.semiauto._

case class HealthCheckResponse(message: String)

object HealthCheckResponse {

  implicit val healthCheckResponseEncoder: Encoder[HealthCheckResponse] =
    deriveEncoder

}
