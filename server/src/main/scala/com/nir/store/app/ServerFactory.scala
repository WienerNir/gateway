package com.nir.store.app

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.nir.store.http.CommonDirectives
import com.nir.store.routes.{HealthcheckerRoute, StoreRoute}
import com.nir.store.service.posts.models.Post
import scala.concurrent.ExecutionContext

import scala.collection.mutable

object ServerFactory {

  def create(implicit actorSystem: akka.actor.ActorSystem,
             ec: ExecutionContext): Route = {
    val db = new mutable.HashMap[String, Post]()
    CommonDirectives.routeRoot(StoreRoute.resource(db).routes) ~ HealthcheckerRoute.resource.routes

  }
}
