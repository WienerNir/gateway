package com.nir.gateway.http.models

import com.nir.gateway.retries.models.RetryPolicy

case class HttpConfig(retryPolicy: RetryPolicy, baseUri: String)
