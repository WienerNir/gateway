package com.nir.store.service.posts.operators

import com.nir.store.UnitSpec
import com.nir.store.operators.Operator.BinaryOperator.{And, Or}
import com.nir.store.operators.Operator.LeafOperator.{
  Equal,
  GreaterThan,
  LessThan
}
import com.nir.store.operators.Operator.UnaryOperator.Not
import com.nir.store.service.posts.operators.properties.PostProperty.{
  IdProperty,
  ViewsProperty
}

class OperatorBuilderImplSpec extends UnitSpec {

  it should "return Or operator" in {
    val input = "OR(EQUAL(id,test),GREATER_THAN(views,4))"
    OperatorBuilderImpl.fromString(input) shouldBe Or(
      Equal(IdProperty, "test"),
      GreaterThan(ViewsProperty, 4)
    )

  }

  it should "return And operator" in {
    val input = "AND(EQUAL(id,test),GREATER_THAN(views,4))"
    OperatorBuilderImpl.fromString(input) shouldBe And(
      Equal(IdProperty, "test"),
      GreaterThan(ViewsProperty, 4)
    )
  }

  it should "return Not operator" in {
    val input = "NOT(EQUAL(id,test))"
    OperatorBuilderImpl.fromString(input) shouldBe Not(
      Equal(IdProperty, "test")
    )

  }

  it should "return Equal operator" in {
    val input = "EQUAL(id,test)"
    OperatorBuilderImpl.fromString(input) shouldBe Equal(IdProperty, "test")
  }

  it should "return LessThan operator" in {
    val input = "LESS_THAN(views,4)"
    OperatorBuilderImpl.fromString(input) shouldBe LessThan(ViewsProperty, 4)
  }

  it should "return GreaterThan operator" in {
    val input = "GREATER_THAN(views,4)"
    OperatorBuilderImpl.fromString(input) shouldBe GreaterThan(ViewsProperty, 4)
  }

}
