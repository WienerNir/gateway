package com.nir.store.monitor

import org.slf4j._

trait Logging {
  implicit protected lazy val logger: Logger =
    LoggerFactory.getLogger(this.getClass)
}
