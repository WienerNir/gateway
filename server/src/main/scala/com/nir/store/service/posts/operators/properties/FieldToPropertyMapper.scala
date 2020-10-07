package com.nir.store.service.posts.operators.properties

import com.nir.store.service.posts.operators.properties.PostProperty._

object FieldToPropertyMapper {

  def toProperty(field: String) =
    field match {
      case "tittle"    => TittleProperty
      case "id"        => IdProperty
      case "views"     => ViewsProperty
      case "content"   => ContentProperty
      case "timestamp" => TimestampProperty
    }
}
