package com.nir.gateway.retries

import com.nir.gateway.monitor.Logging
import com.nir.gateway.retries.models.RetryPolicy
import retry.Success

import scala.concurrent.{ExecutionContext, Future}

object RetryClient extends Logging {

  def backoff[T](
    retryPolicy: RetryPolicy,
    f: => Future[T]
  )(implicit success: Success[T], ec: ExecutionContext): Future[T] = {
    retry.Backoff(retryPolicy.max, retryPolicy.delay, retryPolicy.base).apply {
      () =>
        f
    }
  }

}
