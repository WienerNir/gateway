package com.nir.gateway.http

import akka.http.scaladsl.model.HttpResponse

import scala.concurrent.Future

trait HttpClient {

  def get(uriSuffix: String,
          headers: List[(String, String)] = Nil): Future[HttpResponse]

  def post(uriSuffix: String,
           headers: List[(String, String)] = Nil,
           json: String = ""): Future[HttpResponse]
}
