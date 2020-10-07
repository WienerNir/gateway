package com.nir.store.service.posts.operators

import com.nir.store.operators.Operator
import com.nir.store.operators.Operator.BinaryOperator.{And, Or}
import com.nir.store.operators.Operator.LeafOperator.{
  Equal,
  GreaterThan,
  LessThan,
  NotEqual
}

object NegateOperator {

  def negate(operator: Operator): Operator = {
    operator match {
      case Or(a, b)          => And(negate(a), negate(b))
      case And(a, b)         => Or(negate(a), negate(b))
      case LessThan(a, b)    => Or(GreaterThan(a, b), Equal(a, b.toString))
      case GreaterThan(a, b) => Or(LessThan(a, b), Equal(a, b.toString))
      case Equal(a, b)       => NotEqual(a, b)
    }
  }

}
