package com.nir.store.service.posts.models.responses

import com.nir.store.service.posts.models.Post
import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

case class SearchResponse(posts: List[Post])

object SearchResponse {

  implicit val encoder: Encoder[SearchResponse] =
    deriveEncoder[SearchResponse]

}
