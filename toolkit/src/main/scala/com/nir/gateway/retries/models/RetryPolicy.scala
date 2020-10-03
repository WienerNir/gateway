package com.nir.gateway.retries.models

import retry.Defaults

import scala.concurrent.duration.FiniteDuration

case class RetryPolicy(max: Int = 8,
                       delay: FiniteDuration = Defaults.delay,
                       base: Int = 2)
