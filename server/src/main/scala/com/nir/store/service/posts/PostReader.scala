package com.nir.store.service.posts

import com.nir.store.dao.Reader
import com.nir.store.monitor.Logging
import com.nir.store.operators.Operator
import com.nir.store.operators.Operator.BinaryOperator.{And, Or}
import com.nir.store.operators.Operator.LeafOperator.{
  Equal,
  GreaterThan,
  LessThan,
  NotEqual
}
import com.nir.store.operators.Operator.UnaryOperator.Not
import com.nir.store.service.posts.models.Post
import com.nir.store.service.posts.operators.NegateOperator
import com.nir.store.service.posts.operators.properties.PropertyToValueMapper

import scala.collection.mutable.HashMap
import scala.concurrent.ExecutionContext

private[posts] class PostReader(db: HashMap[String, Post])(
  implicit ec: ExecutionContext
) extends Reader[Operator, Post]
    with Logging {

  override def search(request: Operator): List[Post] =
    runSearch(request, db.values.toList)

  private def runSearch(request: Operator, list: List[Post]): List[Post] = {
    request match {

      case And(a, b) => {
        val reducedList = runSearch(a, list)
        runSearch(b, reducedList)
      }

      case Or(a, b) => (runSearch(a, list) ++ runSearch(b, list)).distinct

      case Not(a) => runSearch(NegateOperator.negate(a), list)

      case LessThan(property, value) =>
        list.filter(p => PropertyToValueMapper.getIntValue(property, p) < value)

      case GreaterThan(property, value) =>
        list.filter(p => PropertyToValueMapper.getIntValue(property, p) > value)

      case Equal(property, value) =>
        list.filter(
          p => PropertyToValueMapper.getStringValue(property, p) == value
        )

      case NotEqual(property, value) =>
        list.filterNot(
          p => PropertyToValueMapper.getStringValue(property, p) == value
        )

    }
  }

}

object PostReader {}
