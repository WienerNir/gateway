package com.nir.store.service.posts.operators.properties

import com.nir.store.operators.Property
import com.nir.store.service.posts.models.Post
import com.nir.store.service.posts.operators.properties.PostProperty.{
  ContentProperty,
  IdProperty,
  TimestampProperty,
  TittleProperty,
  ViewsProperty
}

object PropertyToValueMapper {

  def getStringValue(property: Property, post: Post): String = {
    property match {
      case TittleProperty    => post.title
      case IdProperty        => post.id
      case ContentProperty   => post.content
      case ViewsProperty     => post.views.toString
      case TimestampProperty => post.timestamp.toString
    }
  }

  def getIntValue[T](property: Property, post: Post): Int = {
    property match {
      case ViewsProperty     => post.views
      case TimestampProperty => post.timestamp
    }
  }
}
