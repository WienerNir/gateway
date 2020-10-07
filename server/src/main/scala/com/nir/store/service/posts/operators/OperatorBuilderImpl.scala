package com.nir.store.service.posts.operators

import com.nir.store.operators.Operator
import com.nir.store.operators.Operator.BinaryOperator.{And, Or}
import com.nir.store.operators.Operator.LeafOperator.{
  Equal,
  GreaterThan,
  LessThan
}
import com.nir.store.operators.Operator.UnaryOperator.Not
import com.nir.store.service.posts.operators.exceptions.InvalidOperatorException
import com.nir.store.service.posts.operators.properties.FieldToPropertyMapper

object OperatorBuilderImpl extends OperatorBuilder {

  override def fromString(s: String): Operator = {

    val UnaryPattaren =
      "(NOT)\\(([EQUAL|LESS_THAN|GREATER_THAN|AND|OR|NOT].*)\\)".r

    val BinaryPattaren =
      "(AND|OR)\\(([EQUAL|LESS_THAN|GREATER_THAN|AND|OR|NOT].*),([EQUAL|LESS_THAN|GREATER_THAN|AND|OR|NOT].*)\\)".r

    val LeafOperationPattaren =
      "(EQUAL|LESS_THAN|GREATER_THAN)\\((.*),(.*)\\)".r

    s match {
      case BinaryPattaren(operator, a, b) => {
        val first = fromString(a)
        val second = fromString(b)
        buildBinaryOperator(operator, first, second)
      }

      case UnaryPattaren(operator, a) => {
        val input = fromString(a)
        buildUnaryOperator(operator, input)
      }

      case LeafOperationPattaren(o, a, b) => {
        buildLeafOperator(o, a, b)
      }

      case _ => throw InvalidOperatorException("unsupported operator")
    }
  }

  private def buildLeafOperator(operatorType: String,
                                field: String,
                                value: String): Operator = {
    val property = FieldToPropertyMapper.toProperty(field)
    operatorType match {
      case "GREATER_THAN" => GreaterThan(property, value.toInt)
      case "LESS_THAN"    => LessThan(property, value.toInt)
      case "EQUAL"        => Equal(property, value)
    }

  }

  private def buildBinaryOperator(operatorType: String,
                                  operatorA: Operator,
                                  operatorB: Operator): Operator = {
    operatorType match {
      case "OR"  => Or(operatorA, operatorB)
      case "AND" => And(operatorA, operatorB)
    }
  }

  private def buildUnaryOperator(operatorType: String,
                                 operator: Operator): Operator = {
    operatorType match {
      case "NOT" => Not(operator)
    }
  }
}
