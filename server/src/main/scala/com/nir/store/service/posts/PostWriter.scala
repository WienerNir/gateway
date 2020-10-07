package com.nir.store.service.posts

import com.nir.store.monitor.Logging
import com.nir.store.dao.Writer
import com.nir.store.service.posts.dao.{DB, PostDb}
import com.nir.store.service.posts.models.Post

import scala.concurrent.ExecutionContext

private[posts] class PostWriter(db: DB)(implicit ec: ExecutionContext)
    extends Writer[Post, Unit]
    with Logging {

  override def write(post: Post): Unit = {
    db.save(post)
  }
}

object PostWriter {

  def create(db: PostDb)(implicit ec: ExecutionContext) = {
    new PostWriter(db)
  }
}
