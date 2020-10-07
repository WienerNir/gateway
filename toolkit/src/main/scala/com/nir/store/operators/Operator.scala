package com.nir.store.operators

sealed trait Operator

object Operator {

  sealed trait BinaryOperator extends Operator

  object BinaryOperator {

    case class Or(operatorA: Operator, operatorB: Operator)
        extends BinaryOperator
    case class And(operatorA: Operator, operatorB: Operator)
        extends BinaryOperator

  }

  sealed trait UnaryOperator extends Operator

  object UnaryOperator {
    case class Not(operatorA: Operator) extends UnaryOperator
  }

  sealed trait LeafOperator extends Operator

  object LeafOperator {
    case class Equal(property: Property, value: String) extends LeafOperator
    case class NotEqual(property: Property, value: String) extends LeafOperator
    case class GreaterThan(property: Property, value: Int) extends LeafOperator
    case class LessThan(property: Property, value: Int) extends LeafOperator
  }

}
