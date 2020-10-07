package com.nir.store.service.posts.operators

import com.nir.store.monitor.Logging
import com.nir.store.operators.Operator

trait OperatorBuilder extends Logging {

  def fromString(operator: String): Operator

}
