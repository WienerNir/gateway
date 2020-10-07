package com.nir.store.routes

import com.nir.store.monitor.Logging
import com.nir.store.service.posts.PostService
import com.nir.store.service.posts.formats.UrlParameterFormats
import com.nir.store.service.posts.models.Post
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.syntax._

import scala.collection.mutable
import scala.concurrent.ExecutionContext
import akka.actor.ActorSystem
import akka.http.scaladsl.model.{
  ContentTypes,
  HttpEntity,
  HttpResponse,
  StatusCodes
}
import akka.http.scaladsl.server.Directives.{
  complete,
  get,
  handleWith,
  parameters,
  path,
  post,
  _
}
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class StoreRoute(service: PostService)(
  implicit executionContext: ExecutionContext,
  actorSystem: ActorSystem,
  mat: Materializer
) extends Logging
    with FailFastCirceSupport {

  def routes: Route = postRoute ~ searchPostsRoute

  private val postRoute: Route =
    path("store") {
      post {
        handleWith(service.post)
      }
    }

  private val searchPostsRoute: Route =
    path("store") {
      (get & parameter('query.as(UrlParameterFormats.operatorUnmarshaller))) {
        searchRequest =>
          val featureResult = service.search(searchRequest)
          onComplete(featureResult) {
            case Success(result) => {
              complete(
                HttpResponse(
                  entity = HttpEntity(
                    ContentTypes.`application/json`,
                    result.asJson.toString
                  )
                )
              )
            }
            case Failure(e) =>
              complete(
                StatusCodes.InternalServerError -> "something went wrong"
              )
          }
      }
    }

}

object StoreRoute {

  def resource(db: mutable.HashMap[String, Post])(
    implicit executionContext: ExecutionContext,
    actorSystem: ActorSystem,
    mat: Materializer
  ) =
    new StoreRoute(PostService.create(db))

}
