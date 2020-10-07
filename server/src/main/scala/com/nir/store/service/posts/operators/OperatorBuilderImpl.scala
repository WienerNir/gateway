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
      "(not)\\(([equal|less_than|greater_than|and|or|not].*)\\)".r

    val BinaryPattaren =
      "(and|or)\\(([equal|less_than|greater_than|and|or|not].*),([equal|less_than|greater_than|and|or|not].*)\\)".r

    val LeafOperationPattaren =
      "(equal|less_than|greater_than)\\((.*),(.*)\\)".r

    s.toLowerCase.replaceAll("\\s", "") match {
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

      case _ => throw new InvalidOperatorException("unsupported operator")
    }
  }

  private def buildLeafOperator(operatorType: String,
                                field: String,
                                value: String): Operator = {
    val property = FieldToPropertyMapper.toProperty(field)
    operatorType match {
      case "greater_than" => GreaterThan(property, value.toInt)
      case "less_than"    => LessThan(property, value.toInt)
      case "equal"        => Equal(property, value)
    }

  }

  private def buildBinaryOperator(operatorType: String,
                                  operatorA: Operator,
                                  operatorB: Operator): Operator = {
    operatorType match {
      case "or"  => Or(operatorA, operatorB)
      case "and" => And(operatorA, operatorB)
    }
  }

  private def buildUnaryOperator(operatorType: String,
                                 operator: Operator): Operator = {
    operatorType match {
      case "not" => Not(operator)
    }
  }
}
