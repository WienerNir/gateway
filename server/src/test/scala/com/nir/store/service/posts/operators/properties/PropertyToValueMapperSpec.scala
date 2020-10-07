package com.nir.store.service.posts.operators.properties

import com.nir.store.UnitSpec
import com.nir.store.service.posts.models.Post
import com.nir.store.service.posts.operators.properties.PostProperty.{
  ContentProperty,
  IdProperty,
  TimestampProperty,
  TittleProperty,
  ViewsProperty
}

class PropertyToValueMapperSpec extends UnitSpec {

  val post = Post("1", "content_test", 123323, "test_title", 1)

  it should "return title value when receiving TittleProperty" in {
    PropertyToValueMapper.getStringValue(TittleProperty, post) shouldBe post.title
  }

  it should "return id value when receiving IdProperty" in {
    PropertyToValueMapper.getStringValue(IdProperty, post) shouldBe post.id
  }

  it should "return views value as string when receiving ViewsProperty" in {
    PropertyToValueMapper.getStringValue(ViewsProperty, post) shouldBe post.views.toString
  }

  it should "return content value when receiving ContentProperty" in {
    PropertyToValueMapper.getStringValue(ContentProperty, post) shouldBe post.content
  }

  it should "return timestamp value as string when receiving TimestampProperty" in {
    PropertyToValueMapper.getStringValue(TimestampProperty, post) shouldBe post.timestamp.toString
  }

  it should "return timestamp value as int when receiving ViewsProperty" in {
    PropertyToValueMapper.getIntValue(ViewsProperty, post) shouldBe post.views
  }

  it should "return timestamp value as int when receiving TimestampProperty" in {
    PropertyToValueMapper.getIntValue(TimestampProperty, post) shouldBe post.timestamp
  }

}
