package com.nir.store.service.posts.operators

import com.nir.store.UnitSpec
import com.nir.store.operators.Operator.BinaryOperator.{And, Or}
import com.nir.store.operators.Operator.LeafOperator.{
  Equal,
  GreaterThan,
  LessThan,
  NotEqual
}
import com.nir.store.service.posts.operators.properties.PostProperty.{
  ContentProperty,
  IdProperty,
  TittleProperty,
  ViewsProperty
}

class NegateOperatorSpec extends UnitSpec {

  it should "return And with not equal operator when receiving or with equal operator" in {
    NegateOperator.negate(
      Or(Equal(IdProperty, "1"), Equal(TittleProperty, "titte"))
    ) shouldBe
      And(NotEqual(IdProperty, "1"), NotEqual(TittleProperty, "titte"))
  }

  it should "return or with not equal operator when receiving and with equal operator" in {
    NegateOperator.negate(
      And(Equal(IdProperty, "1"), Equal(TittleProperty, "title"))
    ) shouldBe
      Or(NotEqual(IdProperty, "1"), NotEqual(TittleProperty, "title"))
  }

  it should "return or with greaterThan and equal operator when receiving lessThan operator" in {
    NegateOperator.negate(LessThan(ViewsProperty, 1)) shouldBe
      Or(GreaterThan(ViewsProperty, 1), Equal(ViewsProperty, "1"))
  }

  it should "return or with lessThan and equal operator when receiving greaterThan operator" in {
    NegateOperator.negate(GreaterThan(ViewsProperty, 1)) shouldBe
      Or(LessThan(ViewsProperty, 1), Equal(ViewsProperty, "1"))
  }

  it should "return notEqual operator when receiving equal operator" in {
    NegateOperator.negate(Equal(ContentProperty, "content")) shouldBe
      NotEqual(ContentProperty, "content")
  }

}
