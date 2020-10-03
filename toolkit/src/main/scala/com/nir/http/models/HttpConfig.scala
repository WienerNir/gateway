package com.nir.http.models

import com.nir.retries.models.RetryPolicy

case class HttpConfig(retryPolicy: RetryPolicy, baseUri: String)
