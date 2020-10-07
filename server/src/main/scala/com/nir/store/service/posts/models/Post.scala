package com.nir.store.service.posts.models

import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder

case class Post(id: String,
                content: String,
                timestamp: Int,
                title: String,
                views: Int)

object Post {

  implicit val encoder: Encoder[Post] =
    deriveEncoder[Post]

}
