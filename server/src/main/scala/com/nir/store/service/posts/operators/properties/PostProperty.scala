package com.nir.store.service.posts.operators.properties

import com.nir.store.operators.Property

sealed trait PostProperty extends Property

object PostProperty {

  case object TittleProperty extends PostProperty
  case object IdProperty extends PostProperty
  case object ViewsProperty extends PostProperty
  case object ContentProperty extends PostProperty
  case object TimestampProperty extends PostProperty

}
