package com.nir.store.service.posts

import com.nir.store.UnitSpec
import com.nir.store.dao.{Reader, Writer}
import com.nir.store.operators.Operator
import com.nir.store.operators.Operator.LeafOperator.Equal
import com.nir.store.service.posts.models.Post
import com.nir.store.service.posts.models.requests.{PostRequest, SearchRequest}
import com.nir.store.service.posts.operators.properties.PostProperty.IdProperty

class PostServiceSpec extends UnitSpec {

  val writer = stub[Writer[Post, Unit]]
  val reader = stub[Reader[Operator, Post]]

  val subject = new PostService(writer, reader)

  it should "call writer when receiving create " in {
    val postRequest = PostRequest("1", "tittle", "content_test", 1, 13233233)
    (writer.write _).when(*).returns(()).once()
    subject.post(postRequest)
  }

  it should "call search when receiving search" in {
    val searchRequest = SearchRequest(Equal(IdProperty, "1"))
    (reader.search _).when(*).returns(Set.empty).once()
    subject.search(searchRequest)
  }

}
