package com.nir.store.service.posts

import com.nir.store.UnitSpec
import com.nir.store.operators.Operator.BinaryOperator.{And, Or}
import com.nir.store.operators.Operator.LeafOperator.{
  Equal,
  GreaterThan,
  LessThan
}
import com.nir.store.operators.Operator.UnaryOperator.Not
import com.nir.store.service.posts.dao.PostDb
import com.nir.store.service.posts.models.Post
import com.nir.store.service.posts.operators.properties.PostProperty.{
  IdProperty,
  ViewsProperty
}

import scala.collection.mutable.Set
import scala.collection.mutable

class PostReaderSpec extends UnitSpec {

  val db = new PostDb(
    new mutable.HashMap[String, Post],
    new mutable.HashMap[String, mutable.Set[String]],
    new mutable.HashMap[String, mutable.Set[String]],
    new mutable.HashMap[String, mutable.Set[String]],
    new mutable.HashMap[String, mutable.Set[String]]
  )

  val post1 = Post("1", "content_test", 123323, "test_title", 1)
  val post2 = Post("2", "content_test", 123323, "test_title", 4)
  val post3 = Post("3", "content_test", 123323, "test_title", 1)

  db.save(post1)
  db.save(post2)
  db.save(post3)

  val subject = new PostReader(db)

  it should "return post with id 1" in {
    val request = Equal(IdProperty, "\"1\"")
    subject.search(request).head shouldBe post1
  }

  it should "return posts with id 1 and 2" in {
    val request = Or(Equal(IdProperty, "\"1\""), Equal(IdProperty, "\"2\""))
    subject.search(request) shouldBe Set(post1, post2)
  }

  it should "return posts with id 2 and 3" in {
    val request = Not(Equal(IdProperty, "\"1\""))
    subject.search(request) shouldBe Set(post2, post3)
  }

  it should "return posts with id 3" in {
    val request = And(LessThan(ViewsProperty, 2), Equal(IdProperty, "\"3\""))
    subject.search(request) shouldBe Set(post3)
  }

  it should "return posts with id 2" in {
    val request = And(GreaterThan(ViewsProperty, 3), Equal(IdProperty, "\"2\""))
    subject.search(request) shouldBe Set(post2)
  }

}
