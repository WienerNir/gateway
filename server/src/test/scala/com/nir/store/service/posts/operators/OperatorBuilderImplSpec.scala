package com.nir.store.service.posts.operators

import com.nir.store.UnitSpec
import com.nir.store.operators.Operator.BinaryOperator.{And, Or}
import com.nir.store.operators.Operator.LeafOperator.{
  Equal,
  GreaterThan,
  LessThan
}
import com.nir.store.operators.Operator.UnaryOperator.Not
import com.nir.store.service.posts.operators.exceptions.InvalidOperatorException
import com.nir.store.service.posts.operators.properties.PostProperty.{
  IdProperty,
  ViewsProperty
}

class OperatorBuilderImplSpec extends UnitSpec {

  it should "return Or operator" in {
    val input = "or(equal(id,test),greater_than(views,4))"
    OperatorBuilderImpl.fromString(input) shouldBe Or(
      Equal(IdProperty, "test"),
      GreaterThan(ViewsProperty, 4)
    )

  }

  it should "return And operator" in {
    val input = "and(equal(id,test),greater_than(views,4))"
    OperatorBuilderImpl.fromString(input) shouldBe And(
      Equal(IdProperty, "test"),
      GreaterThan(ViewsProperty, 4)
    )
  }

  it should "return Not operator" in {
    val input = "not(equal(id,test))"
    OperatorBuilderImpl.fromString(input) shouldBe Not(
      Equal(IdProperty, "test")
    )

  }

  it should "return Equal operator" in {
    val input = "equal(id,test)"
    OperatorBuilderImpl.fromString(input) shouldBe Equal(IdProperty, "test")
  }

  it should "return LessThan operator" in {
    val input = "less_than(views,4)"
    OperatorBuilderImpl.fromString(input) shouldBe LessThan(ViewsProperty, 4)
  }

  it should "return GreaterThan operator" in {
    val input = "greater_than(views,4)"
    OperatorBuilderImpl.fromString(input) shouldBe GreaterThan(ViewsProperty, 4)
  }

}
