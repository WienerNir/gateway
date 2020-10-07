package com.nir.store.service.posts.dao

import com.nir.store.UnitSpec
import com.nir.store.service.posts.models.Post
import com.nir.store.service.posts.operators.properties.PostProperty.{
  ContentProperty,
  TimestampProperty,
  TittleProperty,
  ViewsProperty
}
import cats.implicits._
import scala.collection.mutable
import scala.collection.mutable.Set

class PostDbSpec extends UnitSpec {

  val db = new PostDb(
    new mutable.HashMap[String, Post],
    new mutable.HashMap[String, Set[String]],
    new mutable.HashMap[String, Set[String]],
    new mutable.HashMap[String, Set[String]],
    new mutable.HashMap[String, Set[String]]
  )

  val post = Post("1", "content test", 123323, "test title", 1)
  val updatePost = Post("1", "content test v2", 120, "test title v2", 2)

  it should "save post and index fields" in {
    db.save(post)
    db.getById("1") shouldBe post.some
    db.getByIndex(ContentProperty, "\"content test\"").head shouldBe post
    db.getByIndex(ViewsProperty, "1").head shouldBe post
    db.getByIndex(TittleProperty, "\"test title\"").head shouldBe post
    db.getByIndex(TimestampProperty, "123323").head shouldBe post
  }

  it should "update post and index fields" in {
    db.save(post)
    db.save(updatePost)
    db.getById("1") shouldBe updatePost.some
    db.getByIndex(TimestampProperty, "120").head shouldBe updatePost
    db.getByIndex(TittleProperty, "\"test title v2\"").head shouldBe updatePost
    db.getByIndex(ContentProperty, "\"content test v2\"")
      .head shouldBe updatePost
    db.getByIndex(ViewsProperty, "2").head shouldBe updatePost
  }

}
