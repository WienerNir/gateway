package com.nir.store.service.posts.operators.exceptions

case class InvalidOperatorException(message: String)
    extends Exception(
      s"""Failed parsing operator:|errors: $message""".stripMargin
    )
