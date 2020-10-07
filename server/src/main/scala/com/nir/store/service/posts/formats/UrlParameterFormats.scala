package com.nir.store.service.posts.formats

import akka.http.scaladsl.unmarshalling.Unmarshaller
import com.nir.store.monitor.Logging
import com.nir.store.service.posts.models.requests.SearchRequest
import com.nir.store.service.posts.operators.OperatorBuilderImpl

object UrlParameterFormats extends Logging {

  val operatorUnmarshaller: Unmarshaller[String, SearchRequest] =
    Unmarshaller.strict[String, SearchRequest](query => {
      logger.info(s"$query")
      val operator =
        OperatorBuilderImpl.fromString(query)
      SearchRequest(operator)
    })
}
