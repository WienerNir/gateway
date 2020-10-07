package com.nir.store.service.posts

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.nir.store.dao.{Reader, Writer}
import com.nir.store.operators.Operator
import com.nir.store.service.posts.models.requests.{PostRequest, SearchRequest}
import com.nir.store.service.posts.models.Post
import io.scalaland.chimney.dsl._
import cats.implicits._
import com.nir.store.monitor.Logging
import com.nir.store.service.posts.models.responses.SearchResponse

import scala.collection.mutable.HashMap
import scala.concurrent.{ExecutionContext, Future}

class PostService(writer: Writer[Post, Unit], reader: Reader[Operator, Post])(
  implicit executionContext: ExecutionContext
) extends Logging {

  def post(postRequest: PostRequest): Future[Unit] = {
    writer.write(postRequest.into[Post].transform)
    ().pure[Future]
  }

  def search(searchRequest: SearchRequest): Future[SearchResponse] = {
    val posts = reader.search(searchRequest.operator)
    logger.info(s"Search resulted to: $posts")
    SearchResponse(posts).pure[Future]
  }
}

object PostService {

  def create(db: HashMap[String, Post])(
    implicit executionContext: ExecutionContext,
    actorSystem: ActorSystem,
    mat: Materializer
  ) =
    new PostService(new PostWriter(db), new PostReader(db))
}
