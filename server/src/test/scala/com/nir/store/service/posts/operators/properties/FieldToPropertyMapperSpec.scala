package com.nir.store.service.posts.operators.properties

import com.nir.store.UnitSpec
import com.nir.store.service.posts.operators.properties.PostProperty.{
  ContentProperty,
  IdProperty,
  TimestampProperty,
  TittleProperty,
  ViewsProperty
}

class FieldToPropertyMapperSpec extends UnitSpec {

  it should "return TittleProperty when receiving tittle" in {
    FieldToPropertyMapper.toProperty("title") shouldBe TittleProperty
  }

  it should "return IdProperty when receiving views" in {
    FieldToPropertyMapper.toProperty("views") shouldBe ViewsProperty
  }

  it should "return ViewsProperty when receiving id" in {
    FieldToPropertyMapper.toProperty("id") shouldBe IdProperty
  }

  it should "return ContentProperty when receiving content" in {
    FieldToPropertyMapper.toProperty("content") shouldBe ContentProperty
  }

  it should "return TimestampProperty when receiving timestamp" in {
    FieldToPropertyMapper.toProperty("timestamp") shouldBe TimestampProperty
  }

}
