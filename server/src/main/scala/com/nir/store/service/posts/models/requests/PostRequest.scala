package com.nir.store.service.posts.models.requests

import io.circe.generic.semiauto.deriveDecoder
import io.circe.Decoder

case class PostRequest(id: String,
                       title: String,
                       content: String,
                       views: Int,
                       timestamp: Int)

object PostRequest {

  implicit val decoder: Decoder[PostRequest] =
    deriveDecoder[PostRequest]

}
