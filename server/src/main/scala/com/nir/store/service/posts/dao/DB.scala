package com.nir.store.service.posts.dao

import com.nir.store.operators.Property
import com.nir.store.service.posts.models.Post
import scala.collection.mutable

trait DB {

  def getPosts(): Iterable[Post]

  def getByIndex(indexType: Property, key: String): Predef.Set[Post]

  def getById(key: String): Option[Post]

  def save(post: Post): Unit

}
