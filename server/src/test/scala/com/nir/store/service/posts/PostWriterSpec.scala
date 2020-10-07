package com.nir.store.service.posts

import com.nir.store.UnitSpec
import com.nir.store.service.posts.models.Post

import scala.collection.mutable
import scala.collection.mutable.Set
import cats.implicits._
import com.nir.store.service.posts.dao.PostDb

class PostWriterSpec extends UnitSpec {

  val db = new PostDb(
    new mutable.HashMap[String, Post],
    new mutable.HashMap[String, Set[String]],
    new mutable.HashMap[String, Set[String]],
    new mutable.HashMap[String, Set[String]],
    new mutable.HashMap[String, Set[String]]
  )
  val post1 = Post("1", "content_test", 123323, "test_title", 1)
  val post1Update = Post("1", "content_test_v2", 3, "test_title_v2", 2)

  val subject = new PostWriter(db)

  it should "create post1 in db" in {
    subject.write(post1)
    db.getById("1") shouldBe post1.some
  }

  it should "update post1 in db" in {
    subject.write(post1)
    subject.write(post1Update)
    db.getById("1") shouldBe post1Update.some
  }

}
